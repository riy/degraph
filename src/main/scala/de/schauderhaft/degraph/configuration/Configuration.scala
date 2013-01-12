package de.schauderhaft.degraph.configuration

import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike
import org.rogach.scallop.exceptions.ScallopException
import de.schauderhaft.degraph.filter.IncludeExcludeFilter
import de.schauderhaft.degraph.filter.RegExpFilter
import de.schauderhaft.degraph.categorizer.InternalClassCategorizer
import de.schauderhaft.degraph.categorizer.PatternMatchingCategorizer
import de.schauderhaft.degraph.categorizer.PackageCategorizer
import de.schauderhaft.degraph.categorizer.MultiCategorizer.combine

object Configuration {
    def apply(args: Array[String]): Either[String, Configuration] = {
        var errorMessage: Option[String] = None
        val commandLine = CommandLineParser.parse(args)
        commandLine.initialize { case ScallopException(m) => errorMessage = Some(m + "\nUsage:\n" + commandLine.builder.help) }
        errorMessage match {
            case Some(m) => Left(m)
            case _ => Right(Configuration(
                classpath = commandLine.classpath(),
                includes = commandLine.includeFilter(),
                excludes = commandLine.excludeFilter(),
                categories = Map("default" -> commandLine.groupings().map(UnnamedPattern(_)))))
        }
    }
}

case class Configuration(
    classpath: String,
    includes: Seq[String],
    excludes: Seq[String],
    categories: Map[String, Seq[Pattern]]) {

    def createGraph(analyzer: AnalyzerLike) =
        analyzer.analyze(classpath, buildCategorizer(categories), buildFilter(includes, excludes))

    private[this] def buildFilter(includes: Seq[String],
        excludes: Seq[String]) = {
        new IncludeExcludeFilter(
            includes.map((x: String) => RegExpFilter.filter(x.r)).toSet,
            excludes.map((x: String) => RegExpFilter.filter(x.r)).toSet)
    }

    private[this] def buildCategorizer(categories: Map[String, Seq[Pattern]]): (AnyRef => AnyRef) = {
        val ps = for { (level, patterns) <- categories }
            yield buildCategorizer(level, patterns)
        combine(ps.toSeq: _*)
    }

    private[this] def buildCategorizer(categoryLevel: String, groupings: Seq[Pattern]): (AnyRef => AnyRef) = {
        val groupingCats = groupings.map((p: Pattern) => new PatternMatchingCategorizer(categoryLevel, p.pattern))
        val categorizers = List(InternalClassCategorizer, PackageCategorizer) ++ groupingCats
        combine(categorizers: _*)
    }

}

sealed trait Pattern {
    def pattern: String
}
case class UnnamedPattern(val pattern: String) extends Pattern
case class NamedPattern(val pattern: String, name: String) extends Pattern