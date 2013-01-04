package de.schauderhaft.degraph.categorizer

import de.schauderhaft.degraph.analysis.Node

class PatternMatchingCategorizer(targetType: String, pattern: String) {
    private[this] val Pattern = ensureParens(escapeDots(pattern)).replace("*", """[^\.]*""").r

    def apply(x: AnyRef): AnyRef = x match {
        case n: Node => matches(n.name).map(Node(targetType, _)).getOrElse(n)
        case _ => x
    }

    private[this] def matches(name: String) = {
        //        val MetaPattern = """(.*)\((.*)\)(.*)""".r
        //        val MetaPattern(prefix, matchGroup, suffix) = pattern

        name match {
            case Pattern(matchedGroup) => Some(matchedGroup)
            case _ => None
        }
    }

    private[this] def escapeDots(p: String) = p //p.replace(".", """\.""")

    private[this] def ensureParens(p: String) =
        if (""".*\(.*\).*""".r.findFirstIn(p).isDefined)
            p
        else
            "(" + p + ")"

}