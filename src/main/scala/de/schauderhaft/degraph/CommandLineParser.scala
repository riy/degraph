package de.schauderhaft.degraph
import org.rogach.scallop._
import org.rogach.scallop.exceptions.UnknownOption
import org.rogach.scallop.exceptions.ScallopException
import org.rogach.scallop.exceptions.Version
import org.rogach.scallop.exceptions.Help

object CommandLineParser {

    class Conf(args: Seq[String]) extends ScallopConf(args) {

        version("Degraph prerelease. Use at own risk.")
        banner("""Degraph analyses class and jar files and creates graphml documents out of it
for visualizing dependencies.

See https://github.com/schauder/Dependency-Manager for details""")
        val output = opt[String]("output",
            default = Some("output.graphml"),
            descr = "the file name of the output file")
        val classpath = opt[String]("classpath",
            default = Some("."),
            descr = "the classpath where degraph looks for classfiles to analyze")
        val excludeFilter = opt[List[String]]("excludeFilter",
            default = Some(List()),
            descr = "if this argument is given those nodes that are matched by the given regular expression will get excluded from the graph.")
        val includeFilter = opt[List[String]]("includeFilter",
            default = Some(List()),
            descr = "if this argument is given, only those nodes get included in the resulting graph, that match the given regular expression.")
        val groupings = opt[List[String]]("groupings", default = Some(List()))
        verify

        override protected def onError(e: Throwable): Unit = e match {
            case Version =>
                builder.vers.foreach(println)
            case _ =>
                builder.printHelp
        }
        //        System.exit(1)
    }

    def parse(args: Array[String]) = {
        new Conf(args)
    }
}