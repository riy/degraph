package de.schauderhaft.degraph.configuration

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import de.schauderhaft.degraph.categorizer.NamedPatternMatchingCategorizer
import de.schauderhaft.degraph.categorizer.PatternMatchingCategorizer

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