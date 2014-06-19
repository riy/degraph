

package de.schauderhaft.degraph.configuration

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.graph.NodeTestUtil._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConstraintViolationTest extends FunSuite {
  test("toString without violations") {
    val cv = ConstraintViolation("aSliceType", "constraintName")
    cv.toString should be("[aSliceType](constraintName):")
  }

  test("toString with violations") {
    val cv = ConstraintViolation("aSliceType", "constraintName", (n("a"), n("b")), (n("x"), n("y")))
    cv.toString should be("""[aSliceType](constraintName):
    a -> b
    x -> y""")
  }
}