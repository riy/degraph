package de.schauderhaft.degraph.configuration

object ConfigurationParser {
    def parse(input: String): Configuration = Configuration(None, Seq(), Seq(), Map(), None)
}