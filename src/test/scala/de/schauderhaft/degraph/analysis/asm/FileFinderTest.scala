package de.schauderhaft.degraph.analysis.asm

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._
import java.io.File
import org.junit.BeforeClass
import org.scalatest.BeforeAndAfterAll
import org.scalatest.prop.PropertyChecks

@RunWith(classOf[JUnitRunner])
class FileFinderTest extends FunSuite
  with BeforeAndAfterAll {

  val userDir = new File(System.getProperty("user.dir")).getCanonicalPath()
  val filesForTest = Set("A.class", "J.jar", "T.txt")

  override def beforeAll() = {
    // create some Files to be used in the test
    for (f <- filesForTest)
      new File(userDir, f).createNewFile() should be(true);

  }

  override def afterAll() = {
    for (f <- filesForTest)
      new File(userDir, f).delete() should be(true)
  }

  test("FileFinder returns an empty Set if it finds nothing") {
    new FileFinder("/thisPathShouldNotExist/").find() should be(Set[File]())
  }

  test("FileFinder should find File in user dir when searching for file in current directory") {
    new FileFinder(".").find() should contain(new File(userDir, "A.class"))
  }

  test("FileFinder should only find class or jar files") {
    new FileFinder(".").find().foreach(
      f => f.getName() should (endWith(".class") or endWith(".jar")))
  }

  test("FileFinder should find the class file") {
    new FileFinder(".").find()
      .filter(_.getName().endsWith(".class")) should not be ('empty)
  }

  test("FileFinder should find the jar file") {
    new FileFinder(".").find()
      .filter(_.getName().endsWith(".jar")) should not be ('empty)
  }

}