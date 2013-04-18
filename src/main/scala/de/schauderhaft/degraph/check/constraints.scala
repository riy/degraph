package de.schauderhaft.degraph.check
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.graph.SliceSource
import scalax.collection.edge.LkDiEdge
import scalax.collection.mutable.{ Graph => SGraph }
import scalax.collection.GraphPredef.anyToNode
import scalax.collection.mutable.{ Graph => SGraph }
import de.schauderhaft.degraph.configuration.Configuration
import de.schauderhaft.degraph.configuration.Constraint

/**
 * provides a DSLish method of creating Layer instances
 */
object Layer {
    def anyOf(es: String*) = LenientLayer(es: _*)
}

/**
 * one or more slices making up an architectural layer, i.e. a group of slices all constraint in the same way.
 */
sealed trait Layer {
    def contains(elem: String): Boolean
    def denyDependenciesWithinLayer: Boolean
}

abstract class SimpleLayer(es: String*) extends Layer {
    private[this] val eSet = es.toSet
    def contains(elem: String): Boolean = eSet.contains(elem)
}

/**
 * a layer where the elements of that layer may depend on each other
 */
case class LenientLayer(es: String*) extends SimpleLayer(es: _*) {
    val denyDependenciesWithinLayer = false
}

/**
 * a layer where the elements of that may NOT depend on each other
 */
case class StrictLayer(es: String*) extends SimpleLayer(es: _*) {
    val denyDependenciesWithinLayer = true
}

/**
 * a constraint that applies only to a certain sliceType.
 *
 */
trait SlicedConstraint extends Constraint {
    def sliceType: String

    /**
     * a predicate determining if a given node pair violates this constraint.
     *
     * Will get called with nodes from the slice type of this constraint
     */
    def isViolatedBy(n1: Node, n2: Node): Boolean

    protected def slices: IndexedSeq[Layer]
    protected def indexOf(n: Node) = n match {
        case sn: SimpleNode => slices.indexWhere(_.contains(sn.name))
        case _ => throw new IllegalStateException("Sorry, I thought this would never happen, please report a bug including the callstack")
    }

    protected def constraitContainsBothNodes(i: Int, j: Int) =
        i >= 0 && j >= 0

    /**
     * implemented in such a way that all dependencies of the specified slice type will be iterated and @link isViolatedBy called for each dependency
     */
    def violations(ss: SliceSource): Set[(Node, Node)] = {
        val sg = ss.slice(sliceType)
        (for {
            eT <- sg.edges.toSeq
            val e = eT.value
            if (isViolatedBy(e._1, e._2))
        } yield (e._1.value, e._2.value)).toSet
    }
}

/**
 * each layer (A) mentioned in this constraint may only depend on a layer (B) when A comes before B
 *
 * Dependencies from and to slices not represented by a layer aren't constraint by this constraint
 */
case class LayeringConstraint(sliceType: String, slices: IndexedSeq[Layer]) extends SlicedConstraint {
    def isViolatedBy(n1: Node, n2: Node) = {
        val i1 = indexOf(n1)
        val i2 = indexOf(n2)
        constraitContainsBothNodes(i1, i2) &&
            (i1 > i2 ||
                (n1 != n2 && i1 == i2 && slices(i1).denyDependenciesWithinLayer))
    }

}

/**
 * each layer (A) mentioned in this constraint may only depend on a layer (B) when A comes directly before B
 */
case class DirectLayeringConstraint(sliceType: String, slices: IndexedSeq[Layer]) extends SlicedConstraint {
    def isViolatedBy(n1: Node, n2: Node) = {
        val i1 = indexOf(n1)
        val i2 = indexOf(n2)
        (constraitContainsBothNodes(i1, i2) &&
            (i1 > i2 ||
                i2 - i1 > 1 || (n1 != n2 && i1 == i2 && slices(i1).denyDependenciesWithinLayer))) ||
                (i1 < 0 && i2 > 0) ||
                (i1 >= 0 && i1 < slices.size - 1 && i2 < 0)
    }
}

