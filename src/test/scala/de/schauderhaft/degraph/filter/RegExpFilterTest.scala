package de.schauderhaft.degraph.filter

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers.be
import org.scalatest.matchers.ShouldMatchers.convertToAnyShouldWrapper
import de.schauderhaft.degraph.analysis.dependencyFinder.Convert
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.Node._

@RunWith(classOf[JUnitRunner])
class RegExpFilterTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    val filter = RegExpFilter.filter("""some.*Test""".r)

    test("returns false when nothing matches") {
        filter(classNode("in.a.package.TridentTest")) should be(false)
    }

    test("returns true when class name matches") {
        filter(classNode("in.a.package.TrisomeTest")) should be(true)
    }

    test("returns true when package name matches") {
        filter(classNode("in.someTest.package.Blah")) should be(true)
    }

    test("returns true when package and class name matches together") {
        filter(classNode("in.some.package.BlahTest")) should be(true)
    }

    test("returns true when passed a matching package") {
        filter(packageNode("in.some.packageTest")) should be(true)
    }

    test("does not match on the node Type") {
        filter(Node("in.some.packageTest", "x")) should be(false)
    }

    test("otherwise matches on the toString Method") {
        val testObject = new AnyRef { override def toString = "this is some weird Test object" }

        filter(testObject) should be(true)
        filter(new AnyRef) should be(false)
    }
}
