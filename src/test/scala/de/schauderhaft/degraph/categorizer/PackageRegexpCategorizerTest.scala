package de.schauderhaft.degraph.categorizer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node._

@RunWith(classOf[JUnitRunner])
class PackageRegexpCategorizerTest extends FunSuite with ShouldMatchers {

    val cat = PackageRegexpCategorizer.pattern("""de.schauderhaft\.(.*)\..*""".r)
    //                                            de.schauderhaft.(.*)

    test("Pattern categorizer returns parameter for not matching String") {
        cat("some String") should be("some String")
    }

    test("Pattern categorizer categorizes  matching String with the parameter") {
        val string = "de.schauderhaft.module.test"
        cat(string) should be(string)
    }

    test("Pattern categorizer categorizes not matching class node correctly") {
        val node = classNode("de.blah.test.Class")
        cat(node) should be(node)
    }
    test("Pattern categorizer categorizes  matching class node with the parameter") {
        val node = classNode("de.schauderhaft.module.test.Class")
        cat(node) should be(node)
    }

    test("Pattern categorizer categorizes  matching package node with Package Node based on the matchgroup") {
        val classNode = packageNode("de.schauderhaft.module.test")
        cat(classNode) should be(packageNode("module"))
    }
}
