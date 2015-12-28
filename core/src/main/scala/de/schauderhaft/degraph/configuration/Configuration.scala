package de.schauderhaft.degraph.configuration

import de.schauderhaft.degraph.analysis.{AnalyzerLike, IncludeExcludeFilter}
import de.schauderhaft.degraph.configuration.Slicer.toSlicer
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.slicer.MultiCategorizer.combine
import de.schauderhaft.degraph.slicer.{CombinedSlicer, InternalClassCategorizer, PackageCategorizer, ParallelCategorizer, PatternMatchingFilter}


/**
  * represents all the information configurable in commandline arguments and configuration files for Degraph.
  */
case class Configuration(
  classpath: Option[String] = None,
  includes: Seq[String] = Seq(),
  excludes: Seq[String] = Seq(),
  categories: Map[String, Seq[Pattern]] = Map(),
  output: PrintConfiguration = NoPrinting(),
  constraint: Set[Constraint] = Set(CycleFree),
  analyzer: AnalyzerLike = null
) {

  lazy val slicing = buildCategorizer(categories)

  def createGraph() =
    analyzer.analyze(classpath.get, slicing, buildFilter(includes, excludes))

  def valid = classpath.isDefined && output != NoPrinting()

  private[this] def buildFilter(
    includes: Seq[String],
    excludes: Seq[String]
  ) = {
    new IncludeExcludeFilter(
      includes.map((x: String) => new PatternMatchingFilter(x)).toSet,
      excludes.map((x: String) => new PatternMatchingFilter(x)).toSet
    )
  }

  private[this] def buildCategorizer(categories: Map[String, Seq[Pattern]]): (AnyRef => Node) = {
    val slicers = for {(level, patterns) <- categories}
      yield buildCategorizer(level, patterns)
    val slicersWithPackages = new ParallelCategorizer(PackageCategorizer +: slicers.toSeq: _*)
    combine(InternalClassCategorizer, slicersWithPackages)
  }

  private[this] def buildCategorizer(slicing: String, groupings: Seq[Pattern]): (AnyRef => Node) =
    new CombinedSlicer(groupings.map(toSlicer(slicing, _)): _*)

  override def toString = {
    def line(l: String, v: String) = l + " = " + v + "%n".format()

    def lineO(l: String, c: Option[String]) = c match {
      case None => ""
      case Some(s) => line(l, s)
    }

    def lineS(l: String, c: Iterable[String]) =
      if (c.isEmpty) ""
      else line(l, c.mkString(", "))

    "Configuration{%s%s%s%s%s%s}".format(
      lineO("classpath", classpath),
      lineS("includes", includes),
      lineS("excludes", excludes),
      lineS("categories", categories.map(t => t._1 + " " + t._2.mkString(", "))),
      line(
        "output", output match {
          case NoPrinting() => ""
          case p: Print => p.path
        }
      ),
      lineS("constraints", constraint.map(_.shortString))
    )
  }
}

sealed trait Pattern {
  def pattern: String
}

case class UnnamedPattern(val pattern: String) extends Pattern

case class NamedPattern(val pattern: String, name: String) extends Pattern {
  if (name.contains("*") || name.contains(".")) {
    println("You use '" + name + "' as the NAME of a NamedPattern. Please note that the NAME comes last in such a pattern.")
  }
}

