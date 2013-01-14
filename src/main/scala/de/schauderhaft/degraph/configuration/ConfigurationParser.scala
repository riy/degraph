package de.schauderhaft.degraph.configuration

import scala.util.parsing.combinator.RegexParsers

object ConfigurationParser

class ConfigurationParser extends RegexParsers {
    def parse(input: String): Configuration = {
        parseAll(definition, input) match {
            case s: Success[Configuration] => s.get
            case f => throw new RuntimeException("Failed to parse" + f)
        }
    }

    def defs: Parser[Configuration] = definition.* ^^ mayReduce

    def definition: Parser[Configuration] = output | include

    override val whiteSpace = """[ \t]*""".r

    private def line(key: String): Parser[String] = (key + "=") ~> "\\S*".r
    protected def output: Parser[Configuration] = line("output") ^^ ((x: String) => Configuration(None, Seq(), Seq(), Map(), Some(x)))
    protected def include: Parser[Configuration] = line("include") ^^ ((x: String) => Configuration(None, Seq(x), Seq(), Map(), None))
    protected def emptyLine: Parser[String] = "^\\s$".r

    protected def merge(c1: Configuration, c2: Configuration): Configuration =
        Configuration(
            c1.classpath.orElse(c2.classpath),
            c1.includes ++ c2.includes,
            c1.excludes ++ c2.excludes,
            c1.categories ++ c2.categories,
            c1.output.orElse(c2.output))

    private def combine(s: Seq[Configuration]): Configuration =
        s.reduce(merge(_, _))

    private val mayReduce = new PartialFunction[Seq[Configuration], Configuration] {
        def isDefinedAt(s: Seq[Configuration]) =
            s.filter(_.output.isDefined).size <= 1 &&
                s.filter(_.classpath.isDefined).size <= 1
        def apply(s: Seq[Configuration]) = combine(s)
    }
}