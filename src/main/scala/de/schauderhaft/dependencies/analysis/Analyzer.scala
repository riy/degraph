package de.schauderhaft.dependencies.analysis
import java.util.Collections

import scala.collection.JavaConversions.collectionAsScalaIterable
import scala.collection.JavaConversions.mapAsScalaMap

import com.jeantessier.classreader.LoadListenerVisitorAdapter
import com.jeantessier.classreader.TransientClassfileLoader
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.CodeDependencyCollector
import com.jeantessier.dependency.FeatureNode
import com.jeantessier.dependency.Node
import com.jeantessier.dependency.NodeFactory

import de.schauderhaft.dependencies.graph.Graph

class Analyzer {
    def analyze(sourceFolder : String) : Graph = {
        def debug(text : String, from : Node, to : Node) {
            val prefix = List("de.schauderhaft.dependencies.example", "org.junit")
            def shouldPrint(node : Node) =
                prefix.exists(node.getName.startsWith(_))
            if (shouldPrint(from) && shouldPrint(to))
                println("%s: %s --> %s".format(text, from.getName, to.getName))
        }
        val loader = new TransientClassfileLoader()
        val factory = new NodeFactory()
        val visitor = new CodeDependencyCollector(factory)
        loader.addLoadListener(new LoadListenerVisitorAdapter(visitor))
        loader.load(Collections.singleton(sourceFolder))

        val classes : scala.collection.mutable.Map[String, ClassNode] = factory.getClasses

        val g = new Graph()
        // different ways to find classes a class depends on.
        val navigations = List(
            (c : ClassNode) => c.getParents(), // finds superclasses
            (c : ClassNode) => c.getOutboundDependencies()) // finds classes of fields

        for ((_, c) <- classes) {
            g.add(c)

            for (
                nav <- navigations;
                n <- nav(c).toTraversable
            )  g.connect(c, n)


            // finds members including constructors
            val features : Traversable[FeatureNode] = c.getFeatures()
            features.foreach { f =>
                {
                    val dependencies : Traversable[Node] = f.getOutboundDependencies()
                    dependencies.foreach { d =>
                        g.connect(c, d)
                        d match {
                            case f2 : FeatureNode => g.connect(c, f2.getClassNode())
                            case _                =>
                        }
                    }

                }
            }
        }
        return g
    }
}