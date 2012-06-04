package de.schauderhaft.dependencies
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
        val excludeFilter = opt[String]("excludeFilter", default = Some(""))
        val includeFilter = opt[String]("includeFilter", default = Some(""))
        val groupings = opt[List[String]]("groupings", default = Some(List()))
    }

    def parse(args : Array[String]) = {
        new Conf(args)
    }
}