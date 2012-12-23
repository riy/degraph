package de.schauderhaft.degraph

import scala.xml.XML
import de.schauderhaft.degraph.analysis.Analyzer
import de.schauderhaft.degraph.categorizer.InternalClassCategorizer
import de.schauderhaft.degraph.categorizer.MultiCategorizer.combine
import de.schauderhaft.degraph.categorizer.PackageCategorizer
import de.schauderhaft.degraph.categorizer.PackageRegexpCategorizer.pattern
import de.schauderhaft.degraph.filter.IncludeExcludeFilter
import de.schauderhaft.degraph.filter.RegExpFilter
import de.schauderhaft.degraph.writer.Writer
import org.rogach.scallop.exceptions.UnknownOption
import org.rogach.scallop.exceptions.ScallopException
import org.rogach.scallop.exceptions.Version

/**
 * the main class of the DependencyManager, plugging everything together, starting the analysis process and writing the result to an XML file
 */
object Degraph {

    def main(args: Array[String]): Unit = {
        val config = CommandLineParser.parse(args)
        try {
            config.initialize {
                case ScallopException(message) =>
                    println(message)
                    config.printHelp
                    System.exit(1)
            }
            val cat = buildCategorizer(config.groupings())
            val g = Analyzer.analyze(config.classpath(),
                cat,
                buildFilter(config.includeFilter(), config.excludeFilter()))

            val xml = (new Writer()).toXml(g)
            XML.save(config.output(), xml, "UTF8", true, null)
        }
    }

    private def buildCategorizer(groupings: List[String]) = {
        val groupingCats = groupings.map((s: String) => pattern(s.r))
        val categorizers = groupingCats ++ List(InternalClassCategorizer, PackageCategorizer)
        combine(categorizers: _*)
    }

    private def buildFilter(includes: List[String],
        excludes: List[String]) = {
        new IncludeExcludeFilter(
            includes.map((x: String) => RegExpFilter.filter(x.r)).toSet,
            excludes.map((x: String) => RegExpFilter.filter(x.r)).toSet)
    }
}