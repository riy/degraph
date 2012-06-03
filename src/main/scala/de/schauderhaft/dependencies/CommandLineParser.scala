package de.schauderhaft.dependencies
import org.rogach.scallop._

object CommandLineParser {
    class Conf(args : Array[String]) extends ScallopConf(args) {
        val fileName = opt[String]("output", default = Some("output.graphml"))
        val input = opt[String]("input", default = Some(""))
        val filter = opt[String]("filter", default = Some(""))
    }
    def parse(args : Array[String]) = {
        new Conf(args)
    }
}