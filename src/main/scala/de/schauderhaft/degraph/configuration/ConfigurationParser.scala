package de.schauderhaft.degraph.configuration

import scala.util.parsing.combinator.RegexParsers

object ConfigurationParser

class ConfigurationParser extends RegexParsers {
    def parse(input: String): Configuration = {
        parseAll(output, input).map((output: String) =>
            Configuration(None, Seq(), Seq(), Map(), Some(output))).getOrElse(
            Configuration(None, Seq(), Seq(), Map(), None))
    }

    protected def output: Parser[String] = "output=" ~> ".*".r
}