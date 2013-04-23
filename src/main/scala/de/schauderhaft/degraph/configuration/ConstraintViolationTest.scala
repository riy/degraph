package de.schauderhaft.degraph.configuration

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConstraintViolationTest extends FunSuite with ShouldMatchers {
    test("toString without violations") {
        val cv = ConstraintViolation("aSliceType", "constraintName")
        cv.toString should be("[aSliceType](constraintName):")
    }

    test("toString with violations") {
        val cv = ConstraintViolation("aSliceType", "constraintName", ("a", "b"), ("x", "y"))
        val expected = """[aSliceType](constraintName):
    a -> b
    x -> y"""
        println(expected)
        println(cv.toString)
        val result = cv.toString
        cv.toString should be(expected)
    }
}