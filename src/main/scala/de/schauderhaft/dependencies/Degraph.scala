package de.schauderhaft.dependencies
import de.schauderhaft.dependencies.analysis.Analyzer
import de.schauderhaft.dependencies.writer.Writer
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter
import scala.xml.XML
import de.schauderhaft.dependencies.categorizer.InternalClassCategorizer
import de.schauderhaft.dependencies.categorizer.PackageCategorizer
import de.schauderhaft.dependencies.categorizer.MultiCategorizer.combine
import de.schauderhaft.dependencies.filter.NoJdk
import de.schauderhaft.dependencies.categorizer.PackageRegexpCategorizer.pattern
import scala.util.matching.Regex

/**
 * the main class of the DependencyManager, plugging everything together, starting the analysis process and writing the result to an XML file
 */
object Degraph {

    def main(args : Array[String]) : Unit = {
        val config = CommandLineParser.parse(args)
        val g = Analyzer.analyze(config.classpath(),
            buildCategorizer(config.groupings()),
            NoJdk)

        val xml = (new Writer()).toXml(g)
        XML.save(config.output(), xml, "UTF8", true, null)
    }

    private def buildCategorizer(groupings : List[String]) = {
        val groupingCats = groupings.map((s : String) => pattern(s.r))
        val categorizers = groupingCats ++ List(InternalClassCategorizer, PackageCategorizer)
        combine(categorizers : _*)
    }

}