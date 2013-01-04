package de.schauderhaft.degraph.filter

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers.be
import org.scalatest.matchers.ShouldMatchers.convertToAnyShouldWrapper
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.analysis.dependencyFinder.Convert
import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node._

@RunWith(classOf[JUnitRunner])
class NoJdkTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    test("returns false for packages from java (dependencyFinder)") {
        val packageNode = new PackageNode("java.test", true)

        NoJdk(packageNode) should be(false)
    }

    test("returns false for packages from javax (dependencyFinder)") {
        val packageNode = new PackageNode("javax.test", true)

        NoJdk(packageNode) should be(false)
    }

    test("returns false for packages from java") {
        val packageNode = Convert(new PackageNode("java.test", true))

        NoJdk(packageNode) should be(false)
    }

    test("returns false for packages from javax") {
        val packageNode = Convert(new PackageNode("javax.test", true))

        NoJdk(packageNode) should be(false)
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