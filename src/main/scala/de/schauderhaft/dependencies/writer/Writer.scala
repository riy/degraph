package de.schauderhaft.dependencies.writer

import de.schauderhaft.dependencies.graph.Graph
import scala.xml.Node

object Writer {
    val defaultWriter = {}
}

class Writer(nodeWriter : AnyRef => Node) {

    def this() = this((x : AnyRef) => <x/>)

    def toXml(g : Graph) = {
        <graphml xmlns="http://graphml.graphdrawing.org/xmlns" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:y="http://www.yworks.com/xml/graphml" xmlns:yed="http://www.yworks.com/xml/yed/3" xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd">
            <key for="graphml" id="d0" yfiles.type="resources"/>
            <key for="port" id="d1" yfiles.type="portgraphics"/>
            <key for="port" id="d2" yfiles.type="portgeometry"/>
            <key for="port" id="d3" yfiles.type="portuserdata"/>
            <key attr.name="url" attr.type="string" for="node" id="d4"/>
            <key attr.name="description" attr.type="string" for="node" id="d5"/>
            <key for="node" id="d6" yfiles.type="nodegraphics"/>
            <key attr.name="Beschreibung" attr.type="string" for="graph" id="d7"/>
            <key attr.name="url" attr.type="string" for="edge" id="d8"/>
            <key attr.name="description" attr.type="string" for="edge" id="d9"/>
            <key for="edge" id="d10" yfiles.type="edgegraphics"/>
            <graph edgedefault="directed" id="G">
            {
                g.topNodes.map(nodeWriter(_))
            }
            </graph>
        </graphml>
    }
}
