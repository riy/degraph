package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._
import org.scalatest.FunSuite
import de.schauderhaft.degraph.configuration.UnnamedPattern
import de.schauderhaft.degraph.configuration.NamedPattern
import de.schauderhaft.degraph.configuration.Configuration
import java.util.Collections
import scala.util.Random
import org.scalatest.prop.Tables.Table
import org.scalatest.prop.TableDrivenPropertyChecks

@RunWith(classOf[JUnitRunner])
class ConstraintBuilderTest extends FunSuite with TableDrivenPropertyChecks {

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

  test("filterClasspath leaves only matching entries in class path - single entry"){
    val cb = ConstraintBuilder(config = new Configuration(classpath = Some("""xyz\""")))
    cb.filterClasspath("x*").configuration.classpath should be(Some("""xyz\"""))
    cb.filterClasspath("a*").configuration.classpath should be(Some(""))
  }

  test("filterClasspath leaves only matching entries in class path - multiple entries"){
    val sep = System.getProperty("path.separator")

    val cb = ConstraintBuilder(config = new Configuration(classpath = Some("xyz.jar" + sep + "abc.jar")))
    cb.filterClasspath("x*.jar").configuration.classpath should be(Some("""xyz.jar"""))
  }

  test("noJars configuration contains only directory based entries, path entries from multiple entries get preserved") {

    val pathSeperator = System.getProperty("path.separator")
    val r = new Random(0)

    val pathElements = Table("alpha", "beta/gamma", """x\y""")
    val jarElements = Table("this.jar", "beta/SomeOther.jar")
    val path = r.shuffle(pathElements ++ jarElements).mkString(pathSeperator)

    val cb = ConstraintBuilder(config = new Configuration(classpath = Some(path)))
    forAll(pathElements) { p =>
      cb.noJars.configuration.classpath.get should include(p)
    }
    forAll(jarElements) { p =>
      cb.noJars.configuration.classpath.get should not include (p)
    }
  }
}