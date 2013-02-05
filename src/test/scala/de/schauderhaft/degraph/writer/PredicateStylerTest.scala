package de.schauderhaft.degraph.writer

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.awt.Color
import java.awt.Color.RED

@RunWith(classOf[JUnitRunner])
class PredicateStylerTest extends FunSuite with ShouldMatchers {
    test("returns default node when predicate is false") {
        val style = PredicateStyler.styler(
            _ => false,
            EdgeStyle(Color.RED, 2.0),
            DefaultEdgeStyle)

        style(("x", "y")) should be(DefaultEdgeStyle)
    }

    test("returns highlight node when predicate is true") {
        val style = PredicateStyler.styler(
            _ => true,
            EdgeStyle(Color.RED, 2.0),
            DefaultEdgeStyle)

        style(("x", "y")) should be(EdgeStyle(Color.RED, 2.0))
    }
}