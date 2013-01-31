package de.schauderhaft.degraph.categorizer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers.be
import org.scalatest.matchers.ShouldMatchers.convertToAnyRefShouldWrapper
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.Node._

@RunWith(classOf[JUnitRunner])
class PackageCategorizerTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    test("an arbitrary value gets categorized as an arbitrary value") {
        PackageCategorizer("alfred") should be("alfred")
    }

    test("an arbitrary Node gets categorized as an arbitrary Node") {
        PackageCategorizer(Node("x", "alfred")) should be(Node("x", "alfred"))
    }

    test("the category of a class node is its package node") {
        PackageCategorizer(classNode("some.package.Class")) should be(packageNode("some.package"))
    }
}