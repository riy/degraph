package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration._

import scala.annotation.varargs

/**
  * the basis for the DSL do define constraints on your dependencies in tests.
  */
case class ConstraintBuilder(
  private val config: Configuration = new Configuration(),
  sliceType: String = "",
  includes: Seq[String] = Seq(),
  excludes: Seq[String] = Seq(),
  slicings: Map[String, Seq[Pattern]] = Map(),
  constraints: Set[Constraint] = Set(),
  output: PrintConfiguration = NoPrinting()
) {


  /**
    * When the test fails the analysis results get written to the given path as a graphml file
    *
    * @param path the path or just the file name to use for writing out the graphml-file
    * @return
    */
  def printOnFailure(path: String) = copy(output = Print(path, onConstraintViolationOnly = true))

  def printTo(path: String) = copy(output = Print(path, onConstraintViolationOnly = false))

  private def any2Layer(arg: AnyRef): Layer = arg match {
    case s: String => LenientLayer(s)
    case l: Layer => l
    case _ => throw new IllegalArgumentException("Only arguments of type String or Layer are accepted")
  }

  private def modifyConfig(slices: IndexedSeq[AnyRef], toConstraint: (String, IndexedSeq[Layer]) => Constraint): ConstraintBuilder =
    copy(
      constraints =
        constraints +
          toConstraint(sliceType, slices.map((x: AnyRef) => any2Layer(x)))
    )

  /**
    * allows a chain of dependencies. Any slice specified as an argument may depend on any slice mentioned later in the list of arguments.
    * Dependencies in the opposite direction are forbidden and lead to a test failure
    *
    * Arguments are either Strings, denoting a slice or a {Layer}
    *
    * see also #allowDirect
    */
  @varargs def allow(slices: AnyRef*): ConstraintBuilder =
    modifyConfig(slices.toIndexedSeq, LayeringConstraint)

  /**
    * allows a slice mentioned in the argument list to depend on the directly next element in the argument list.
    *
    *
    *
    * see also #allow
    */
  @varargs def allowDirect(slices: AnyRef*): ConstraintBuilder =
    modifyConfig(slices.toIndexedSeq, DirectLayeringConstraint)

  @varargs def withSlicing(sliceType: String, sls: AnyRef*) = {
    copy(
      sliceType = sliceType, slicings = slicings + (sliceType ->
        sls.map {
          case s: String => UnnamedPattern(s)
          case (n: String, p: String) => NamedPattern(n, p)
          case p: Pattern => p
        })
    )
  }

  /**
    * defines the packages to be included in the analysis
    * @param s an ANT style pattern (using ** and * as wildcards) for packages to be included
    * @return this
    */
  def including(s: String): ConstraintBuilder = copy(includes = includes :+ s)


  /**
    * defines the packages to be excluded in the analysis
    * @param s an ANT style pattern (using ** and * as wildcards) for packages to be included
    * @return this
    */
  def excluding(s: String): ConstraintBuilder = copy(excludes = excludes :+ s)

  /**
    * removes all jar files from the classpath to be analyzed.
    *
    * Everything ending in .jar is considered a jar file.
    */
  def noJars: ConstraintBuilder =
    filterClasspath(!_.endsWith(".jar"))

  /**
    * reduces the classpath to be analyzed to include only those elements matching the pattern.
    *
    * The pattern is can contain * as a wildcard.
    */
  def filterClasspath(pattern: String): ConstraintBuilder = {
    val regexp = pattern.replace("*", ".*").r
    val filter: (String) => Boolean = regexp.pattern.matcher(_).matches()
    filterClasspath(filter)
  }

  /**
    * reduces the classpath to be analyzed to include only those elements matching the filter.
    *
    */
  def filterClasspath(filter: (String) => Boolean): ConstraintBuilder = {
    copy(config = config.copy(classpath = config.classpath.map(filterClasspathString(_, filter))))
  }

  def configuration = config.copy(
    categories = slicings,
    includes = includes,
    excludes = excludes,
    constraint = config.constraint ++ constraints,
    output = output
  )

  private def filterClasspathString(s: String, filter: String => Boolean) = {
    val sep = System.getProperty("path.separator")
    val elements = s.split(sep)
    elements.filter(filter).mkString(sep)
  }


}