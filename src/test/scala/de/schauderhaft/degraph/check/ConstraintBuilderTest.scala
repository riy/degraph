package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._
import org.scalatest.FunSuite
import de.schauderhaft.degraph.configuration.UnnamedPattern
import de.schauderhaft.degraph.configuration.NamedPattern

@RunWith(classOf[JUnitRunner])
class ConstraintBuilderTest extends FunSuite {

  test("simple Pattern ends up In Configuration") {
    val conf = ConstraintBuilder().withSlicing("x", "a.(*).**").configuration
    conf.categories should be(Map("x" -> Seq(UnnamedPattern("a.(*).**"))))
  }
  test("named Pattern ends up In Configuration") {
    val conf = ConstraintBuilder().withSlicing("type", ("name", "pattern")).configuration
    conf.categories should be(Map("type" -> Seq(NamedPattern("name", "pattern"))))
  }

}