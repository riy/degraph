package de.schauderhaft.degraph.configuration

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class PrintConfigurationTest extends FunSuite {

  test("merging NoPrinting with another configuration yields the other configuration") {
    NoPrinting().merge(Print("somewhere")) should be(Print("somewhere"))
  }


  test("merging Printing on failure only and always printing should yield always printing") {
    Print("somewhere", onConstraintViolationOnly = false)
      .merge(Print("somewhere", onConstraintViolationOnly = true)) should not be('onConstraintViolationOnly)
    Print("somewhere", onConstraintViolationOnly = true)
      .merge(Print("somewhere", onConstraintViolationOnly = false)) should not be('onConstraintViolationOnly)
  }

  test("merging two Print instnces results in the path of the first instance") {
    Print("a").merge(Print("b")) should have('path ("a"))
  }
}
