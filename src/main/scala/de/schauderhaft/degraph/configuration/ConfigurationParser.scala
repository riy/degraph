package de.schauderhaft.degraph.configuration

import scala.util.parsing.combinator.RegexParsers

object ConfigurationParser

class ConfigurationParser extends RegexParsers {
    def parse(input: String): Configuration = {
        parseAll(defs, input + "\n") match {
            case s: Success[Configuration] => s.get
            case f => throw new RuntimeException("Failed to parse" + f)
        }
    }

    def defs: Parser[Configuration] = opt(eol) ~> definition.* ^^ mayReduce

    def definition: Parser[Configuration] = output | include

    override val whiteSpace = """[ \t]*""".r
    def eol: Parser[Any] = """(\r?\n)""".r.+

    private def line(key: String): Parser[String] = (key + "=") ~> "\\S*".r <~ eol
    protected def output: Parser[Configuration] = line("output") ^^ ((x: String) => Configuration(None, Seq(), Seq(), Map(), Some(x)))
    protected def include: Parser[Configuration] = line("include") ^^ ((x: String) => Configuration(None, Seq(x), Seq(), Map(), None))
    protected def emptyLine: Parser[Any] = eol
    protected def emptyLines: Parser[Any] = emptyLine.+

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
        case Some(list: Seq[Configuration]) => mayReduce(list)
        case None => Configuration(None, Seq(), Seq(), Map(), None)
    }
}