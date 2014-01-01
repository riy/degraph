package de.schauderhaft.degraph.hamcrest

import org.scalatest.Matchers._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.{ BeMatcher => SMatcher }
import org.hamcrest.{ Matcher => HMatcher }
import org.scalatest.matchers.MatchResult
import org.scalatest.prop.PropertyChecks
import org.hamcrest.Description
import org.hamcrest.StringDescription

@RunWith(classOf[JUnitRunner])
class HamcrestWrapperTest extends FunSuite {
  def sMatcher(result: Boolean) = new SMatcher[String]() {
    override def apply(left: String) = MatchResult(result, "failed", "notFailed")
  }
  test("the wrapper converts a Scalatest Matcher to a Hamcrest Matcher") {
    val hMatcher: HMatcher[String] = HamcrestWrapper(sMatcher(true)) // won't compile if the assumption does not hold
  }

  for (r <- Set(true, false))
    test("Scalatest Matcher and HamcrestMatcher yield the same return Value (%b)".format(r)) {
      HamcrestWrapper(sMatcher(r)).matchesSafely("x") should be(r)
    }

  test("HamcrestMatcher yields the failure message of not matching") {
    val d = new StringDescription()
    HamcrestWrapper(sMatcher(true)).describeMismatchSafely("x", d)
    d.toString should be("failed")
  }

  test("selfdescription contains the class Name of the matcher ... not smart, but I don't know anything better") {
    val d = new StringDescription()
    HamcrestWrapper(sMatcher(true)).describeTo(d)
    d.toString should include("HamcrestWrapperTest")
  }

}