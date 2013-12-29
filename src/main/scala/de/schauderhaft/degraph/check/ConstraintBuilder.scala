package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import de.schauderhaft.degraph.configuration.Constraint
import de.schauderhaft.degraph.configuration.Pattern
import de.schauderhaft.degraph.configuration.UnnamedPattern
import de.schauderhaft.degraph.configuration.NamedPattern
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
  constraints: Set[Constraint] = Set()) {

  private def any2Layer(arg: AnyRef): Layer = arg match {
    case s: String => LenientLayer(s)
    case l: Layer => l
    case _ => throw new IllegalArgumentException("Only arguments of type String or Layer are accepted")
  }

  private def modifyConfig(slices: IndexedSeq[AnyRef], toConstraint: (String, IndexedSeq[Layer]) => Constraint): ConstraintBuilder =
    copy(constraints =
      constraints +
        toConstraint(sliceType, slices.map((x: AnyRef) => any2Layer(x))))

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
   *  allows a slice mentioned in the argument list to depend on the directly next element in the argument list.
   *
   *
   *
   *  see also #allow
   */
  @varargs def allowDirect(slices: AnyRef*): ConstraintBuilder =
    modifyConfig(slices.toIndexedSeq, DirectLayeringConstraint)

  @varargs def withSlicing(sliceType: String, sls: AnyRef*) = {
    copy(sliceType = sliceType, slicings = slicings + (sliceType ->
      sls.map {
        case s: String => UnnamedPattern(s)
        case (n: String, p: String) => NamedPattern(n, p)
      }))
  }

  def including(s: String): ConstraintBuilder = copy(includes = includes :+ s)
  def excluding(s: String): ConstraintBuilder = copy(excludes = excludes :+ s)

  def configuration = config.copy(
    categories = slicings,
    includes = includes,
    excludes = excludes,
    constraint = config.constraint ++ constraints)

}