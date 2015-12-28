package de.schauderhaft.degraph.configuration

import java.io.{ByteArrayOutputStream, PrintStream}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class NamedPatternTest extends FunSuite
with BeforeAndAfterEach
with Matchers {

  def fixture = {
    val baos = new ByteArrayOutputStream()
    val ps: PrintStream = new PrintStream(baos)
    System.setOut(ps)
    (ps, baos)
  }

  var originalOutputStream: PrintStream = null

  override def beforeEach() {
    originalOutputStream = System.out
  }

  override def afterEach() {
    System.out.flush()
    System.setOut(originalOutputStream)
  }

  test("warns not on correct / plausible usage") {
    val (ps, baos) = fixture
    Console.withOut(ps) {
      NamedPattern("pattern.with*stuff", "name")
      System.out.flush()
      baos.toString().trim should be("")
    }
  }

  test("warns on possible mixup of parameters (dots)") {
    val (ps, baos) = fixture
    Console.withOut(ps) {
      NamedPattern("name on wrong position", "pattern.with.dots")
      System.out.flush()
      baos.toString() should include("'pattern.with.dots'")
    }
  }


  test("warns on possible mixup of parameters (stars)") {
    val (ps, baos) = fixture
    Console.withOut(ps) {
      NamedPattern("name on wrong position", "pattern*with*stars")
      System.out.flush()
      baos.toString() should include("'pattern*with*stars'")
    }
  }


}


