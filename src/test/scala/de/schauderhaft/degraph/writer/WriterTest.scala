package de.schauderhaft.degraph.writer

import scala.xml.Elem
import scala.xml.Node
import scala.xml.NodeSeq
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.ShouldMatchers
import de.schauderhaft.degraph.graph.Graph
import java.awt.Color

@RunWith(classOf[JUnitRunner])
class WriterTest extends FunSuite with ShouldMatchers {

    test("SelfTest one") {
        val someXml = <root><subeins>text</subeins></root>
        (someXml \ "subeins").text should be("text")
    }
    test("SelfTest two") {
        val someXml = <root><subeins><subzwei>text</subzwei></subeins></root>
        (someXml \\ "subzwei").head should be(<subzwei>text</subzwei>)
    }

    test("writing an empty graph should have a graph node") {
        val xml = new Writer().toXml(new Graph())
        (xml \ "graph").size should be(1)
    }

    test("writing an empty graph should have key entries") {
        val xml = new Writer().toXml(new Graph())
        val keys = (xml \ "key").map((_ \ "@id")).map(_.text)
        val keySet = keys.map(_.toString).toSet
        val expectedSet = (0 to 10).map("d" + _).toSet

        keySet should be(expectedSet)
    }

    test("writing a simple node creates elements for that node") {
        val g = new Graph()
        g.add("probe")
        val writer = new Writer((x: AnyRef, _) => <nodeElement>{ x }</nodeElement>, new EdgeWriter())
        val xml = writer.toXml(g)
        (xml \ "graph" \ "nodeElement").text should be("probe")
    }

    test("writing two connected nodes creates elements for the nodes plus an edge") {
        val g = new Graph()
        g.connect("probe1", "probe2")
        val writer = new Writer(
            (x: AnyRef, _) => <nodeElement>{ x }</nodeElement>,
            (x: AnyRef, y: AnyRef) => <edgeElement from={ x.toString } to={ y.toString }/>)
        val xml: Elem = writer.toXml(g)
        val nodeText = (xml \ "graph" \ "nodeElement").text
        nodeText should include("probe1")
        nodeText should include("probe2")

        val edge: NodeSeq = xml \ "graph" \ "edgeElement"
        val edgeText = edge.toString
        edgeText should include("edgeElement")
    }

    test("the color and width of the EdgeStyler end up in the edge") {
        val edgeNode = new EdgeWriter((_, _) => EdgeStyle(Color.RED, 2.0))("x", "y")
        val styleNode = edgeNode \ "data" \ "PolyLineEdge" \ "LineStyle"
        styleNode.toString should include("""color="#FF0000"""")
        styleNode.toString should include("""width="2.0"""")
    }
}

