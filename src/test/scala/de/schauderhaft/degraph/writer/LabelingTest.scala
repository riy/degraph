package de.schauderhaft.degraph.writer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node._

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

    test("the label of a Node is the name of the node") {
        Labeling(classNode("some.Class")) should be("some.Class")
    }

    class DummyObject(override val toString: String)
}

