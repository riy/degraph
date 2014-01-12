package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._
import org.scalatest.FunSuite
import de.schauderhaft.degraph.configuration.UnnamedPattern
import de.schauderhaft.degraph.configuration.NamedPattern
import de.schauderhaft.degraph.configuration.Configuration

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

  test("noJars configuration contains only directory based entries, single Jar gets removed") {
    val cb = ConstraintBuilder(config = new Configuration(classpath = Some("x.jar")))
    cb.noJars.configuration.classpath should be(Some(""))
  }

  test("noJars configuration contains only directory based entries, single path gets preserved") {
    val cb = ConstraintBuilder(config = new Configuration(classpath = Some("x")))
    cb.noJars.configuration.classpath should be(Some("x"))
  }

  test("noJars configuration contains only directory based entries, single path gets preserved when path ends in slash") {
    val cb = ConstraintBuilder(config = new Configuration(classpath = Some("x/")))
    cb.noJars.configuration.classpath should be(Some("x/"))
  }
  test("noJars configuration contains only directory based entries, single path gets preserved when path ends in backslash") {
    val cb = ConstraintBuilder(config = new Configuration(classpath = Some("""x\""")))
    cb.noJars.configuration.classpath should be(Some("""x\"""))
  }
}