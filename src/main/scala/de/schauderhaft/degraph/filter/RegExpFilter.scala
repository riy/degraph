package de.schauderhaft.degraph.filter

import scala.util.matching.Regex
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.analysis.Node

object RegExpFilter {
    def filter(pattern: Regex): AnyRef => Boolean =
        x => x match {
            case n: Node => pattern.findFirstMatchIn(n.name).isDefined
            case cn: ClassNode => pattern.findFirstMatchIn(cn.getPackageNode.getName + "." + cn.getName).isDefined
            case pn: PackageNode => pattern.findFirstMatchIn(pn.getName).isDefined
            case _ => pattern.findFirstMatchIn(x.toString).isDefined
        }

}