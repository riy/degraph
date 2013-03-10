package de.schauderhaft.degraph.analysis.dependencyFinder
import scala.collection.JavaConversions.collectionAsScalaIterable
import scala.collection.JavaConversions.mapAsScalaMap
import scala.collection.JavaConverters._
import com.jeantessier.classreader.LoadListenerVisitorAdapter
import com.jeantessier.classreader.TransientClassfileLoader
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.CodeDependencyCollector
import com.jeantessier.dependency.FeatureNode
import com.jeantessier.dependency.NodeFactory
import de.schauderhaft.degraph.filter.NoSelfReference
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.Node

/**
 * analyzes whatever it finds in the sourceFolder using
 * the Dependency Finder library and returns a Graph instance
 * which captures the relevant dependency information
 *
 */
object Analyzer extends AnalyzerLike {
    def analyze(sourceFolder: String, categorizer: Node => Node, filter: Node => Boolean): Graph = {

        def getRootClasses = {
            val loader = new TransientClassfileLoader()
            val factory = new NodeFactory()
            val visitor = new CodeDependencyCollector(factory)
            loader.addLoadListener(new LoadListenerVisitorAdapter(visitor))
            loader.load(sourceFolder.split(System.getProperty("path.separator")).toSet.asJava)
            factory.getClasses
        }

        val classes: scala.collection.mutable.Map[String, ClassNode] = getRootClasses

        val g = new Graph(categorizer, filter, new NoSelfReference(categorizer))

        val featureOutboundClass = (c: ClassNode) => for (
            f <- c.getFeatures();
            od @ (dummy: FeatureNode) <- f.getOutboundDependencies().toTraversable
        ) yield od.getClassNode()
        // different ways to find classes a class depends on.
        val navigations = List(
            (c: ClassNode) => c.getParents().toTraversable, // finds superclasses
            (c: ClassNode) => c.getOutboundDependencies().toTraversable, // finds classes of fields
            featureOutboundClass)

        for ((_, c) <- classes) {
            val classNode = Convert(c)
            g.add(classNode)

            for (
                nav <- navigations;
                n <- nav(c)
            ) g.connect(classNode, Convert(n))
        }
        return g
    }
}