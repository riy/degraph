package de.schauderhaft.degraph.categorizer
import scala.util.matching.Regex
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.analysis.Node
/**
 * categorizes classes with their respective packages
 */
object PackageRegexpCategorizer {
    def pattern(pattern: Regex): AnyRef => AnyRef = {
        x =>
            {
                val result = purePattern(x, pattern)
                result
            }
    }

    private def purePattern(x: AnyRef, pattern: Regex) = x match {
        case cn: ClassNode =>
            optionMatch(cn.getPackageNode().getName(), pattern).getOrElse(cn)
        case pn: PackageNode =>
            optionMatch(pn.getName(), pattern).getOrElse(pn)
        case n: Node if (n.nodeType == "Package") =>
            optionMatch(n.name, pattern).map(Node("Package", _)).getOrElse(n)
        case _ => x
    }

    private def optionMatch(s: String, pattern: Regex) = {
        val singleMatch = pattern.unapplySeq(s)
        if (singleMatch.size == 1)
            Some(singleMatch.head.head)
        else None
    }
}
