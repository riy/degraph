package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode._

@RunWith(classOf[JUnitRunner])
class PackageCategorizerTest extends FunSuite {
  import org.scalatest.Matchers._

  def n(s: String) = SimpleNode(s, s)

  test("an arbitrary value gets categorized as an arbitrary value") {
    PackageCategorizer(n("alfred")) should be(n("alfred"))
  }

  test("an arbitrary Node gets categorized as an arbitrary Node") {
    PackageCategorizer(SimpleNode("x", "alfred")) should be(SimpleNode("x", "alfred"))
  }

  test("the category of a class node is its package node") {
    PackageCategorizer(classNode("some.package.Class")) should be(packageNode("some.package"))
  }
}