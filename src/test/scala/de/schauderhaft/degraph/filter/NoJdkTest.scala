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

@RunWith(classOf[JUnitRunner])
class NoJdkTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    test("returns true for packages not from java(x) (dependencyFinder)") {
        val packageNode = new PackageNode("de.blah.test", true)
        val classNode = new ClassNode(packageNode, "Class", true)

        NoJdk(classNode) should be(true)
    }

    test("returns false for packages from java (dependencyFinder)") {
        val packageNode = new PackageNode("java.test", true)

        NoJdk(packageNode) should be(false)
    }

    test("returns false for packages from javax (dependencyFinder)") {
        val packageNode = new PackageNode("javax.test", true)

        NoJdk(packageNode) should be(false)
    }

    test("returns false for classes from java (dependencyFinder)") {
        val packageNode = new PackageNode("java.test", true)
        val classNode = new ClassNode(packageNode, "Class", true)

        NoJdk(classNode) should be(false)
    }

    test("returns false for classes from javax (dependencyFinder)") {
        val packageNode = new PackageNode("javax.test", true)
        val classNode = new ClassNode(packageNode, "Class", true)

        NoJdk(classNode) should be(false)
    }

    test("returns true for packages not from java(x)") {
        val packageNode = new PackageNode("de.blah.test", true)
        val classNode = Convert(new ClassNode(packageNode, "Class", true))

        NoJdk(classNode) should be(true)
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
        val packageNode = new PackageNode("java.test", true)
        val classNode = Convert(new ClassNode(packageNode, "Class", true))

        NoJdk(classNode) should be(false)
    }

    test("returns false for classes from javax") {
        val packageNode = new PackageNode("javax.test", true)
        val classNode = Convert(new ClassNode(packageNode, "Class", true))

        NoJdk(classNode) should be(false)
    }

    test("returns true for none class nodes named like a java package") {

        NoJdk(Node("x", "java.stuff")) should be(true)
    }

}