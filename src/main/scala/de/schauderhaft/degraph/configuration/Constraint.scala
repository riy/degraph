package de.schauderhaft.degraph.configuration

import scala.io.Source
import org.rogach.scallop.exceptions.ScallopException
import Slicer.toSlicer
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike
import de.schauderhaft.degraph.slicer.CombinedSlicer
import de.schauderhaft.degraph.slicer.ParallelCategorizer
import de.schauderhaft.degraph.analysis.dependencyFinder.IncludeExcludeFilter
import de.schauderhaft.degraph.slicer.InternalClassCategorizer
import de.schauderhaft.degraph.slicer.PackageCategorizer
import de.schauderhaft.degraph.slicer.MultiCategorizer.combine
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.slicer.PatternMatchingFilter
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode

trait Constraint {
    def sliceType: String

    def isViolatedBy(n1: Node, n2: Node): Boolean

    protected def slices: IndexedSeq[String]
    protected def indexOf(n: Node) = n match {
        case sn: SimpleNode => slices.indexOf(sn.name)
        case _ => throw new IllegalStateException("Sorry, I thought this would never happen, please report a bug including the callstack")
    }

}

case class LayeringConstraint(sliceType: String, slices: IndexedSeq[String]) extends Constraint {
    def isViolatedBy(n1: Node, n2: Node) =
        indexOf(n1) >= 0 &&
            indexOf(n2) >= 0 &&
            indexOf(n1) > indexOf(n2)
}
case class DirectLayeringConstraint(sliceType: String, slices: IndexedSeq[String]) extends Constraint {
    def isViolatedBy(n1: Node, n2: Node) =
        (indexOf(n1) >= 0 &&
            indexOf(n2) >= 0 &&
            (indexOf(n1) > indexOf(n2) ||
                indexOf(n2) - indexOf(n1) > 1)) ||
                ((indexOf(n1) < 0 && indexOf(n2) > 0)) ||
                (indexOf(n1) >= 0 && indexOf(n1) < slices.size - 1 && indexOf(n2) < 0)
}

