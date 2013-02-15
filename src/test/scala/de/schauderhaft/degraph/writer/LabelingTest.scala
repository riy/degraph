package de.schauderhaft.degraph.writer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.Node._
import de.schauderhaft.degraph.model.ParentAwareNode

@RunWith(classOf[JUnitRunner])
class LabelingTest extends FunSuite with ShouldMatchers {

    test("the label of a String with parent None should be the String") {
        Labeling("test", None) should be("test")
    }

    test("the label of an object with parent None should be the toString of that object") {
        Labeling(new DummyObject("test"), None) should be("test")
    }

    test("when the parent + . is the prefix of the node it gets exluded from the label") {
        Labeling("prefix.suffix", Some("prefix")) should be("suffix")
    }

    test("when the parent + $ is the prefix of the node it gets exluded from the label") {
        Labeling("prefix$suffix", Some("prefix")) should be("suffix")
    }

    test("when the parent + $ is the node the node labe does not get change") {
        Labeling("prefix$", Some("prefix")) should be("prefix$")
    }

    test("when the parent + $$ is the prefix of the node it gets exluded from the label") {
        Labeling("prefix$$suffix", Some("prefix")) should be("suffix")
    }

    test("the label of a Node is the name of the node") {
        Labeling(classNode("some.Class")) should be("some.Class")
    }

    test("the label of a ParentAwareNode is the name of the first contained node") {
        Labeling(new ParentAwareNode(packageNode("the package"), classNode("Some Java class"))) should be("the package")
    }

    class DummyObject(override val toString: String)
}

