package de.schauderhaft.degraph.categorizer

class PatternMatcher(pattern: String) {
    private[this] val Pattern = ensureParens(escapeStars(escapeDots(pattern))).r

    def matches(name: String) = {
        name match {
            case Pattern(matchedGroup) => Some(matchedGroup)
            case _ => None
        }
    }

    private[this] def escapeStars(p: String) = {
        val doubleStarPlaceHolder = "&"
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