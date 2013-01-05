package de.schauderhaft.degraph.categorizer

import de.schauderhaft.degraph.analysis.Node

class PatternMatchingCategorizer(targetType: String, pattern: String) {
    private[this] val Pattern = ensureParens(escapeStars(escapeDots(pattern))).r

    def apply(x: AnyRef): AnyRef = x match {
        case n: Node => matches(n.name).map(Node(targetType, _)).getOrElse(n)
        case _ => x
    }

    private[this] def matches(name: String) = {
        name match {
            case Pattern(matchedGroup) => Some(matchedGroup)
            case _ => None
        }
    }

    private[this] def escapeStars(p: String) = {
        val doubleStarPlaceHolder = "|"
        val singleStarPlaceHolder = "§"
        p.
            replace("**", doubleStarPlaceHolder).
            replace("*", singleStarPlaceHolder).
            replace(doubleStarPlaceHolder, """.*""").
            replace(singleStarPlaceHolder, """[^\.]*""")
    }

    private[this] def escapeDots(p: String) = p.replace(".", """\.""")

    private[this] def ensureParens(p: String) =
        if (""".*\(.*\).*""".r.findFirstIn(p).isDefined)
            p
        else
            "(" + p + ")"

}