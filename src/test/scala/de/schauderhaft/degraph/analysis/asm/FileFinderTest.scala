package de.schauderhaft.degraph.analysis.asm

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._
import java.io.File
import org.scalatest.BeforeAndAfterAll

@RunWith(classOf[JUnitRunner])
class FileFinderTest extends FunSuite
  with BeforeAndAfterAll {

  val userDir = new File(System.getProperty("user.dir"))
  val tempDir = new File(userDir, "fileFinderTestDir")
  val subDir = new File(tempDir, "subdir")

  val filesForTest = Set("A.class", "J.jar", "T.txt")

  override def beforeAll() = {
    tempDir.mkdir()
    // create some Files to be used in the test
    for (f <- filesForTest) {
        new File(tempDir, f).createNewFile()
    }

    subDir.mkdir()
    for (f <- filesForTest) {
        new File(subDir, f).createNewFile()
    }
  }

  override def afterAll() = {
    for (f <- filesForTest) {
      new File(tempDir, f).delete()
      new File(subDir, f).delete()
    }
    subDir.delete()
  }

  test("FileFinder returns an empty Set if it finds nothing") {
    new FileFinder(new File(tempDir,"/thisPathShouldNotExist/").getAbsolutePath).find() should be(Set[File]())
  }

  test("FileFinder should find File in user dir when searching for file in current directory") {
    new FileFinder(tempDir.getAbsolutePath).find() should contain(new File(tempDir, "A.class"))
  }

  test("FileFinder should only find class or jar files") {
    new FileFinder(tempDir.getAbsolutePath).find().foreach(
      f => f.getName() should (endWith(".class") or endWith(".jar")))
  }

  test("FileFinder should find the class file") {
    new FileFinder(tempDir.getAbsolutePath).find()
      .filter(_.getName().endsWith(".class")) should not be ('empty)
  }

  test("FileFinder should find the jar file") {
    new FileFinder(tempDir.getAbsolutePath).find()
      .filter(_.getName().endsWith(".jar")) should not be ('empty)
  }

  test("FileFinder should find File in subdir dir when searching for file in current directory") {
    new FileFinder(tempDir.getAbsolutePath).find() should contain(new File(new File(tempDir, "subdir"), "A.class"))
  }
}