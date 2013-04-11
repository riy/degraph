package de.schauderhaft.degraph.configuration

import scala.util.parsing.combinator.RegexParsers

/**
 * parses a configuration file
 */
class ConfigurationParser extends RegexParsers {
    def parse(input: String): Configuration = {
        parseAll(defs, input + "\n") match {
            case s: Success[_] => s.get
            case f => throw new RuntimeException("Failed to parse" + f)
        }
    }

    def defs: Parser[Configuration] = opt(eol) ~> definition.* ^^ mayReduce

    def definition: Parser[Configuration] = comment | output | include | exclude | classpath | slice

    override val whiteSpace = """[ \t]*""".r
    def eol: Parser[Any] = """(\r?\n)""".r.+

    private def line(key: String): Parser[String] = (key ~ "=") ~> word <~ eol
    protected def comment: Parser[Configuration] = "#.*".r <~ eol ^^ ((x: String) => Configuration())
    protected def output: Parser[Configuration] = line("output") ^^ ((x: String) => Configuration(None, Seq(), Seq(), Map(), Some(x)))
    protected def include: Parser[Configuration] = line("include") ^^ ((x: String) => Configuration(None, Seq(x), Seq(), Map(), None))
    protected def exclude: Parser[Configuration] = line("exclude") ^^ ((x: String) => Configuration(None, Seq(), Seq(x), Map(), None))
    protected def classpath: Parser[Configuration] = line("classpath") ^^ ((x: String) => Configuration(Some(x), Seq(), Seq(), Map(), None))
    protected def emptyLine: Parser[Any] = eol
    protected def slice: Parser[Configuration] = (word <~ "=") ~ patternBlock <~ eol ^^ (x => Configuration(categories = Map((x._1, x._2))))
    protected def patternBlock: Parser[List[Pattern]] = "{" ~> eol ~> patterns <~ "}"
    protected def patterns: Parser[List[Pattern]] = rep(pattern)
    protected def pattern: Parser[Pattern] = namedPattern | unnamedPattern
    protected def namedPattern: Parser[NamedPattern] = word ~ word <~ eol ^^ (x => NamedPattern(x._2, x._1))
    protected def unnamedPattern: Parser[UnnamedPattern] = word <~ eol ^^ (x => UnnamedPattern(x))
    protected def word: Parser[String] = "[^\\s{}]+".r

    protected def merge(c1: Configuration, c2: Configuration): Configuration =
        Configuration(
            c1.classpath.orElse(c2.classpath),
            c1.includes ++ c2.includes,
            c1.excludes ++ c2.excludes,
            c1.categories ++ c2.categories,
            c1.output.orElse(c2.output))

    private def combine(s: Seq[Configuration]): Configuration =
        s.fold(Configuration(None, Seq(), Seq(), Map(), None))(merge(_, _))

    private val mayReduce = new PartialFunction[Seq[Configuration], Configuration] {
        def isDefinedAt(s: Seq[Configuration]) =
            s.filter(_.output.isDefined).size <= 1 &&
                s.filter(_.classpath.isDefined).size <= 1
        def apply(s: Seq[Configuration]) = combine(s)
    }

    private def mayReduce(opt: Option[Seq[Configuration]]): Configuration = opt match {
        case Some(list: Seq[_]) => mayReduce(list)
        case None => Configuration(None, Seq(), Seq(), Map(), None)
    }
}