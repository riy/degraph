package de.schauderhaft.dependencies.filter
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode

@RunWith(classOf[JUnitRunner])
class NoJdkTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    test("returns true for packages not from java(x)") {
        val packageNode = new PackageNode("de.blah.test", true)
        val classNode = new ClassNode(packageNode, "Class", true)

        NoJdk(classNode) should be(true)
    }

    test("returns false for packages from java") {
        val packageNode = new PackageNode("java.test", true)

        NoJdk(packageNode) should be(false)
    }

    test("returns false for packages from javax") {
        val packageNode = new PackageNode("javax.test", true)

        NoJdk(packageNode) should be(false)
    }

    test("returns false for classes from java") {
        val packageNode = new PackageNode("java.test", true)
        val classNode = new ClassNode(packageNode, "Class", true)

        NoJdk(classNode) should be(false)
    }

    test("returns false for classes from javax") {
        val packageNode = new PackageNode("javax.test", true)
        val classNode = new ClassNode(packageNode, "Class", true)

        NoJdk(classNode) should be(false)
    }

}