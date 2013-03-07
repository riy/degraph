package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.ParentAwareNode

@RunWith(classOf[JUnitRunner])
class PatternMatchingFilterTest extends FunSuite with ShouldMatchers {

    for (st <- Set("Package", "Class", "other"))
        test("matches the name of Node of type %s".format(st)) {
            val matcher = new PatternMatchingFilter("x.*y.abc.**")

            matcher(SimpleNode(st, "x.y.abc.x")) should be(true)
            matcher(SimpleNode(st, "x.blay.abc.x.yz.D")) should be(true)
            matcher(SimpleNode(st, "x.x.y.abc.x")) should be(false)
        }

    test("matches ParentAwareNode") {
        val matcher = new PatternMatchingFilter("x.*y.abc.**")
        matcher(new ParentAwareNode(new SimpleNode("x", "y"), new SimpleNode("c", "b"))) should be(true)
    }

}