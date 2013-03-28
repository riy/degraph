package de.schauderhaft.degraph.slicer

class PatternMatcher(pattern: String) {
    private[this] val Pattern = ensureParens(escapeStars(escapeDots(pattern))).r

    def matches(name: String) = {
        name match {
            case Pattern(matchedGroup) => Some(matchedGroup)
            case _ => None
        }
    }

    private[this] def escapeStars(p: String) = {
        if (p.contains("***")) throw new IllegalArgumentException("More than two '*'s in a row is not a supported pattern.")
        val doubleStarPlaceHolder = getPlaceHolder(p)
        val singleStarPlaceHolder = getPlaceHolder(p + doubleStarPlaceHolder)
        p.
            replace("**", doubleStarPlaceHolder).
            replace("*", singleStarPlaceHolder).
            replace(doubleStarPlaceHolder, """.*""").
            replace(singleStarPlaceHolder, """[^\.]*""")
    }

    private[this] def getPlaceHolder(pattern: String) =
        ((1).toChar to (200).toChar).find(!pattern.contains(_)).head.toString

    private[this] def escapeDots(p: String) = p.replace(".", """\.""")

    private[this] def ensureParens(p: String) =
        if (""".*\(.*\).*""".r.findFirstIn(p).isDefined)
            p
        else
            "(" + p + ")"
}