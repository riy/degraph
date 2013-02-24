package de.schauderhaft.degraph.writer

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.awt.Color
import java.awt.Color.RED
import de.schauderhaft.degraph.model.SimpleNode

@RunWith(classOf[JUnitRunner])
class PredicateStylerTest extends FunSuite with ShouldMatchers {

    def n(s: String) = SimpleNode(s, s)

    test("returns default node when predicate is false") {
        val style = PredicateStyler.styler(
            _ => false,
            EdgeStyle(Color.RED, 2.0),
            DefaultEdgeStyle)

        style((n("x"), n("y"))) should be(DefaultEdgeStyle)
    }

    test("returns highlight node when predicate is true") {
        val style = PredicateStyler.styler(
            _ => true,
            EdgeStyle(Color.RED, 2.0),
            DefaultEdgeStyle)

        style((n("x"), n("y"))) should be(EdgeStyle(Color.RED, 2.0))
    }
}