package de.schauderhaft.degraph
import org.rogach.scallop._
import org.rogach.scallop.exceptions.UnknownOption

object CommandLineParser {

    class Conf(args : Seq[String]) extends ScallopConf(args) {
        version("Degraph prerelease. Use at own risk.")
        banner("""Degraph analyses class and jar files and creates graphml documents out of it
for visualizing dependencies.

See https://github.com/schauder/Dependency-Manager for details""")
        val output = opt[String]("output", default = Some("output.graphml"))
        val classpath = opt[String]("classpath", default = Some("."))
        val excludeFilter = opt[List[String]]("excludeFilter", default = Some(List()))
        val includeFilter = opt[List[String]]("includeFilter", default = Some(List()))
        val groupings = opt[List[String]]("groupings", default = Some(List()))
    }

    def parse(args : Array[String]) = {
        new Conf(args)
    }
}