package de.schauderhaft.degraph.configuration

import scala.io.Source
import org.rogach.scallop.exceptions.ScallopException
import Slicer.toSlicer
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike
import de.schauderhaft.degraph.slicer.CombinedSlicer
import de.schauderhaft.degraph.slicer.ParallelCategorizer
import de.schauderhaft.degraph.filter.IncludeExcludeFilter
import de.schauderhaft.degraph.filter.RegExpFilter
import de.schauderhaft.degraph.slicer.InternalClassCategorizer
import de.schauderhaft.degraph.slicer.PackageCategorizer
import de.schauderhaft.degraph.slicer.MultiCategorizer.combine
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.slicer.PatternMatchingFilter

object Configuration {
    def apply(args: Array[String]): Either[String, Configuration] = {
        val eitherConfig = fromCommandLine(args)

        eitherConfig
    }
    private def fromCommandLine(args: Array[String]): Either[String, Configuration] = {
        var errorMessage: Option[String] = None
        val commandLine = CommandLineParser.parse(args)
        commandLine.initialize { case ScallopException(m) => errorMessage = Some(m + "\nUsage:\n" + commandLine.builder.help) }
        errorMessage match {
            case Some(m) => Left(m)
            case _ if (commandLine.file.isEmpty) => Right((Configuration(
                classpath = Some(commandLine.classpath()),
                includes = commandLine.includeFilter(),
                excludes = commandLine.excludeFilter(),
                output = Some(commandLine.output()),
                categories = Map())))
            case _ => Right(new ConfigurationParser().parse(Source.fromFile(commandLine.file()).mkString))
        }
    }
}

case class Configuration(
    classpath: Option[String] = None,
    includes: Seq[String] = Seq(),
    excludes: Seq[String] = Seq(),
    categories: Map[String, Seq[Pattern]] = Map(),
    output: Option[String] = None) {

    lazy val slicing = buildCategorizer(categories)

    def createGraph(analyzer: AnalyzerLike) =
        analyzer.analyze(classpath.get, slicing, buildFilter(includes, excludes))

    def valid = classpath.isDefined && output.isDefined

    private[this] def buildFilter(includes: Seq[String],
        excludes: Seq[String]) = {
        new IncludeExcludeFilter(
            includes.map((x: String) => new PatternMatchingFilter(x)).toSet,
            excludes.map((x: String) => new PatternMatchingFilter(x)).toSet)
    }

    private[this] def buildCategorizer(categories: Map[String, Seq[Pattern]]): (AnyRef => Node) = {
        val slicers = for { (level, patterns) <- categories }
            yield buildCategorizer(level, patterns)
        val slicersWithPackages = new ParallelCategorizer(PackageCategorizer +: slicers.toSeq: _*)
        combine(InternalClassCategorizer, slicersWithPackages)
    }

    private[this] def buildCategorizer(slicing: String, groupings: Seq[Pattern]): (AnyRef => Node) =
        new CombinedSlicer(groupings.map(toSlicer(slicing, _)): _*)

}

sealed trait Pattern {
    def pattern: String
}
case class UnnamedPattern(val pattern: String) extends Pattern
case class NamedPattern(val pattern: String, name: String) extends Pattern