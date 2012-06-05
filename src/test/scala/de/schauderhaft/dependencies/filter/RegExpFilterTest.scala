package de.schauderhaft.dependencies.filter

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode

@RunWith(classOf[JUnitRunner])
class RegExpFilterTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    val filter = RegExpFilter.filter("""some.*Test""".r)
    def pack(name : String) = new PackageNode(name, true)
    def clazz(packName : String, name : String) = new ClassNode(pack(packName), name, true)

    test("returns false when nothing matches") {
        filter(clazz("in.a.package", "TridentTest")) should be(false)
    }

    test("returns true when class name matches") {
        filter(clazz("in.a.package", "TrisomeTest")) should be(true)
    }

    test("returns true when package name matches") {
        filter(clazz("in.someTest.package", "Blah")) should be(true)
    }

    test("returns true when package and class name matches together") {
        filter(clazz("in.some.package", "BlahTest")) should be(true)
    }

    test("returns true when passed a matching package") {
        filter(pack("in.some.packageTest")) should be(true)
    }

    test("otherwise matches on the toString Method") {
        val testObject = new AnyRef { override def toString = "this is some weird Test object" }

        filter(testObject) should be(true)
        filter(new AnyRef) should be(false)
    }
}
