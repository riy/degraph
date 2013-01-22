package de.schauderhaft.degraph

import scala.xml.XML
import de.schauderhaft.degraph.analysis.dependencyFinder.Analyzer
import de.schauderhaft.degraph.categorizer.InternalClassCategorizer
import de.schauderhaft.degraph.categorizer.MultiCategorizer.combine
import de.schauderhaft.degraph.categorizer.PackageCategorizer
import de.schauderhaft.degraph.filter.IncludeExcludeFilter
import de.schauderhaft.degraph.filter.RegExpFilter
import de.schauderhaft.degraph.writer.Writer
import org.rogach.scallop.exceptions.UnknownOption
import org.rogach.scallop.exceptions.ScallopException
import org.rogach.scallop.exceptions.Version
import de.schauderhaft.degraph.categorizer.PatternMatchingCategorizer
import de.schauderhaft.degraph.analysis.dependencyFinder.Analyzer
import de.schauderhaft.degraph.configuration.CommandLineParser
import de.schauderhaft.degraph.configuration.Configuration

/**
 * The main class of Degraph, plugging everything together,
 * starting the analysis process and writing the result to an XML file
 */
object Degraph {

    def main(args: Array[String]): Unit = {
        Configuration(args) match {
            case Left(m) => println(m)
            case Right(c) =>
                val g = c.createGraph(Analyzer)
                val xml = (new Writer()).toXml(g)
                XML.save(c.output.get, xml, "UTF8", true, null)
        }
    }

}