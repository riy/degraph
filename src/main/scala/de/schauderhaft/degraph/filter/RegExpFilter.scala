package de.schauderhaft.degraph.filter

import scala.util.matching.Regex
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.analysis.Node

/**
 * filters nodes based on regular expression
 */
object RegExpFilter {
    def filter(pattern: Regex): AnyRef => Boolean =
        x => x match {
            case n: Node => pattern.findFirstMatchIn(n.name).isDefined
            case _ => pattern.findFirstMatchIn(x.toString).isDefined
        }

}