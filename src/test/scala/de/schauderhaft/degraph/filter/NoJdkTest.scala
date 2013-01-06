package de.schauderhaft.degraph.filter

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node._

@RunWith(classOf[JUnitRunner])
class NoJdkTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    test("returns false for packages from java") {
        NoJdk(packageNode("java.test")) should be(false)
    }

    test("returns false for packages from javax") {
        NoJdk(packageNode("javax.test")) should be(false)
    }

    test("returns false for classes from java") {
        NoJdk(classNode("java.test.Class")) should be(false)
    }

    test("returns false for classes from javax") {
        NoJdk(classNode("javax.test.Class")) should be(false)
    }

    test("returns true for none class nodes named like a java package") {

        NoJdk(Node("x", "java.stuff")) should be(true)
    }

}