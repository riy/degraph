package de.schauderhaft.degraph.check

import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MultiElementLenientLayerConstraintTest extends FunSuite with ShouldMatchers {
    val paramTupel = ("t", IndexedSeq(LenientLayer("a"), LenientLayer("b", "c", "d"), LenientLayer("e")))
    val cons = Seq(LayeringConstraint.tupled(paramTupel),
        DirectLayeringConstraint.tupled(paramTupel))

    for (c <- cons) {
        test("dependencies into a multielement layer are ok %s".format(c.getClass())) {
            c.violations(MockSliceSource("t", "a" -> "b", "a" -> "d")) should be(Set())
        }

        test("dependencies from a multielement layer are ok %s".format(c.getClass())) {
            c.violations(MockSliceSource("t", "b" -> "e", "d" -> "e")) should be(Set())
        }
        test("dependencies within a multielement layer are ok %s".format(c.getClass())) {
            c.violations(MockSliceSource("t", "b" -> "c", "b" -> "d")) should be(Set())
        }

        test("self dependencies within a multielement layer are  ok %s".format(c.getClass())) {
            c.violations(MockSliceSource("t", "b" -> "b")) should be(Set())
        }
    }
}
