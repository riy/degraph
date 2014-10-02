package de.schauderhaft.degraph.check

import java.awt.Color._

import de.schauderhaft.degraph.analysis.asm.Analyzer
import de.schauderhaft.degraph.configuration.{Configuration, ConstraintViolation}
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.writer._
import org.scalatest.matchers.{BeMatcher, MatchResult}

import scala.xml.XML

/**
 * provides access to configurations and scalatest matchers useful when testing for dependencies.
 *
 * With all members of Check imported a simple use case looks like this:
 *
 * classpath.forType("module").allow("advertising", "billing", "customer") should be (violationFree)
 *
 * Such a test checks that
 * * all slice types are cycle free.
 * * for the slicetype *module* the slices *advertising* may depend on
 * *billing* and *customer*, and *billing* may depend on *customer*.
 * * Dependencies in other directions are not allowed.
 *
 * Other slices not mentioned in the constraint definition may depend on each other or on the slices in the constraint.
 */
object Check {

  /**
   * a Configuration object containing the current classpath.
   *
   * Intended as a starting point for analyzing the application that is currently running.
   *
   * Note in a typical maven like setup it will also include the test classes as well as all libraries, which might not be desirable.
   * Manipulate the configuration to only contain the classpath elements required or use include and exclude filters for limiting the analyzed classes.
   */
  val classpath = ConstraintBuilder(new Configuration(
    classpath = Option(System.getProperty("java.class.path")),
    analyzer = Analyzer), "package")

  /**
   * a matcher for Configurations testing if the classes specified in the configuration
   * adhere to the dependency constraints configured in the configuration.
   */
  val violationFree: BeMatcher[ConstraintBuilder] = new BeMatcher[ConstraintBuilder] {
    def apply(constraintBuilder: ConstraintBuilder) = {
      val conf = constraintBuilder.configuration
      val g = conf.createGraph()

      def maybePrintGraph(vs: Set[ConstraintViolation]) {
        if (!vs.isEmpty) {
          val styler = PredicateStyler.styler(
            new SlicePredicate(conf.slicing, vs.
              flatMap(_.dependencies)),
            EdgeStyle(RED, 2.0), DefaultEdgeStyle
          )
          val xml = (new Writer(styler)).toXml(g)
          conf.output.foreach(XML.save(_, xml, "UTF8", true, null))
        }
      }

      def checkForViolations(): Set[ConstraintViolation] = {
        val vs = for {
          c <- conf.constraint
          v <- c.violations(g)
        } yield v
        maybePrintGraph(vs)
        vs
      }

      val violations = checkForViolations()

      val matches = violations.isEmpty

      val failureMessage = "%s yields the following constraint violations: %s".format(conf, violations.mkString("%n").format())
      val negativeFailureMessage = "%s does not yield any violations of the constraints.".format(conf)

      new MatchResult(matches, failureMessage, negativeFailureMessage)
    }
  }

  private def sliceNode(edge: (Node, Node)) = {
    val (n1, n2) = edge
    n1.types == n2.types && n1.types.head != "Class"
  }

  private def lowerCaseFirstLetter(s: String) = s.head.toLower + s.tail
}

