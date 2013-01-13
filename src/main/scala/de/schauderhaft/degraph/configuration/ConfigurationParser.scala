package de.schauderhaft.degraph.configuration

import scala.util.parsing.combinator.RegexParsers

class ConfigurationParser extends RegexParsers {
    def parse(input: String): Configuration = {
        Configuration(None, Seq(), Seq(), Map(), None)
    }

    def output: Parser[Any] = "output=" ~> ".*".r
}