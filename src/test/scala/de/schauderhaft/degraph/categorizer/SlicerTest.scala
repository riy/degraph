package de.schauderhaft.degraph.categorizer

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.configuration.NamedPattern
import de.schauderhaft.degraph.configuration.UnnamedPattern

@RunWith(classOf[JUnitRunner])
class SlicerTest extends FunSuite with ShouldMatchers {
    test("NamedPattern to NamedPatternCategorizer") {
        Slicer.toSlicer("t", NamedPattern("p", "n")) should be(new NamedPatternMatchingCategorizer("t", "p", "n"))
    }
    test("UnnamedPattern to PatternMatchingCategorizer") {
        Slicer.toSlicer("t", NamedPattern("p", "n")) should be(new NamedPatternMatchingCategorizer("t", "p", "n"))
    }
    test("NnamedPattern to PatternMatchingCategorizer") {
        Slicer.toSlicer("t", UnnamedPattern("p")) should be(new PatternMatchingCategorizer("t", "p"))
    }
}