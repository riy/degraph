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

  override def beforeAll() = {
    // create some Files to be used in the test

    new File(userDir, "A.class").createNewFile() should be(true);

  }
  override def afterAll() = {
    new File(userDir, "A.class").delete() should be(true)
  }

  test("FileFinder returns an empty Set if it finds nothing") {
    new FileFinder("/thisPathShouldNotExist/").find() should be(Set[File]())
  }

  test("FileFinder should find File in user dir when searching for file in current directory") {
    new FileFinder(".").find() should contain(new File(userDir, "A.class"))
  }

}