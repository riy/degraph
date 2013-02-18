package de.schauderhaft.degraph.analysis.dependencyFinder

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode._

@RunWith(classOf[JUnitRunner])
class ConverterTest extends FunSuite with ShouldMatchers {
    def pack(name: String) = new PackageNode(name, true)
    def clazz(packName: String, name: String) = new ClassNode(pack(packName), name, true)

    test("class in default package get converted to class with name only") {
        Convert(clazz("", "Classname")) should be(classNode("Classname"))
    }

    test("class in other package ignores the package, since the ClassNode contains the full name") {
        Convert(clazz("other", "some.Classname")) should be(classNode("some.Classname"))
    }

    test("package gets converted to package name") {
        Convert(pack("package")) should be(packageNode("package"))
    }

}