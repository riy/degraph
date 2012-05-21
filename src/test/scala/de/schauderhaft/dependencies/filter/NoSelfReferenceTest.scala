package de.schauderhaft.dependencies.filter
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode

@RunWith(classOf[JUnitRunner])
class NoSelfReferenceTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    test("returns true for unrelated objects") {
        NoSelfReference("a", "b") should be(true)
    }

    test("returns false for identical objects") {
        NoSelfReference("a", "a") should be(false)
    }

}