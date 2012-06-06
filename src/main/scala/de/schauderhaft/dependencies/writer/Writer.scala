package de.schauderhaft.dependencies.writer

import de.schauderhaft.dependencies.graph.Graph
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.Elem

object Writer {
    val defaultWriter = {}
}

/**
 * writes a graph to an graphml xml structure which can be displayed by yed in a usable form
 */
class Writer(
        nodeWriter : (AnyRef, Graph) => Node,
        edgeWriter : (AnyRef, AnyRef) => Node) {

    def this(nodeWriter : (AnyRef, Graph) => Node) = this(
        nodeWriter,
        EdgeWriter)

    def this() = this(
        NodeWriter,
        EdgeWriter)

    def toXml(g : Graph) : Elem = {
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
                    g.topNodes.map(nodeWriter(_, g))
                }
                {
                    for {
                        node <- g.allNodes;
                        to <- g.connectionsOf(node)
                    } yield edgeWriter(node, to)
                }
            </graph>
        </graphml>
    }
}

object NodeWriter extends ((AnyRef, Graph) => Node) {
    private def id(n : AnyRef) = n.toString
    private def label(n : AnyRef) = n.toString

    def apply(n : AnyRef, g : Graph) = <node id={ id(n) } yfiles.foldertype="folder">
                                           <data key="d4"/>
                                           <data key="d5"/>
                                           <data key="d6">
                                               <y:ProxyAutoBoundsNode>
                                                   <y:Realizers active="1">
                                                       <y:GroupNode>
                                                           <y:Fill color="#F2F0D8" transparent="false"/>
                                                           <y:BorderStyle color="#000000" type="line" width="1.0"/>
                                                           <y:NodeLabel alignment="right" autoSizePolicy="node_width" backgroundColor="#B7B69E" borderDistance="0.0" fontFamily="Dialog" fontSize="15" fontStyle="plain" hasLineColor="false" modelName="internal" modelPosition="t" textColor="#000000" visible="true" x="0.0" y="0.0">{ label(n) }</y:NodeLabel>
                                                           <y:Shape type="rectangle"/>
                                                           <y:DropShadow color="#D2D2D2" offsetX="4" offsetY="4"/>
                                                           <y:State closed="false" innerGraphDisplayEnabled="false"/>
                                                           <y:Insets bottom="15" bottomF="15.0" left="15" leftF="15.0" right="15" rightF="15.0" top="15" topF="15.0"/>
                                                           <y:BorderInsets bottom="14" bottomF="14.0" left="51" leftF="50.5" right="49" rightF="48.9443359375" top="5" topF="4.7470703125"/>
                                                       </y:GroupNode>
                                                       <y:GroupNode>
                                                           <y:Fill color="#F2F0D8" transparent="false"/>
                                                           <y:BorderStyle color="#000000" type="line" width="1.0"/>
                                                           <y:NodeLabel alignment="right" autoSizePolicy="node_width" backgroundColor="#B7B69E" borderDistance="0.0" fontFamily="Dialog" fontSize="15" fontStyle="plain" hasLineColor="false" modelName="internal" modelPosition="t" textColor="#000000" visible="true" x="0.0" y="0.0">{ label(n) }</y:NodeLabel>
                                                           <y:Shape type="rectangle"/>
                                                           <y:DropShadow color="#D2D2D2" offsetX="4" offsetY="4"/>
                                                           <y:State closed="true" innerGraphDisplayEnabled="false"/>
                                                           <y:Insets bottom="15" bottomF="15.0" left="15" leftF="15.0" right="15" rightF="15.0" top="15" topF="15.0"/>
                                                           <y:BorderInsets bottom="0" bottomF="0.0" left="0" leftF="0.0" right="0" rightF="0.0" top="0" topF="0.0"/>
                                                       </y:GroupNode>
                                                   </y:Realizers>
                                               </y:ProxyAutoBoundsNode>
                                           </data>
                                           <graph edgedefault="directed" id={ "G:" + id(n) }>
                                               {
                                                   g.contentsOf(n).map(apply(_, g))
                                               }
                                           </graph>
                                       </node>
}

object EdgeWriter extends ((AnyRef, AnyRef) => Node) {
    private def id(n : AnyRef) = n.toString
    def apply(source : AnyRef, target : AnyRef) =
        <edge id={ id(source) + "::" + id(target) } source={ id(source) } target={ id(target) }>
        </edge>
}

