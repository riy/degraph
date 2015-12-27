package de.schauderhaft.degraph.app

import _root_.java.awt.Color.RED

import de.schauderhaft.degraph.analysis.asm.Analyzer
import de.schauderhaft.degraph.writer.{DefaultEdgeStyle, EdgeStyle, PredicateStyler, SlicePredicate, Writer}

import scala.xml.XML

/**
  * The main class of Degraph, plugging everything together,
  * starting the analysis process and writing the result to an XML file
  */
object Degraph {

  def main(args: Array[String]): Unit = {
    ConfigurationFromCommandLine(args) match {
      case Left(m) => println(m)
      case Right(c) =>
        val g = c.copy(analyzer = Analyzer).createGraph()
        val violations = c.constraint.
          flatMap(_.violations(g)).
          flatMap(_.dependencies)
        val styler = PredicateStyler.styler(
          new SlicePredicate(c.slicing, violations),
          EdgeStyle(RED, 2.0),
          DefaultEdgeStyle
        )
        val xml = new Writer(styler).toXml(g)
        XML.save(c.output.get, xml, "UTF8", xmlDecl = true, null)
        println("Found %d nodes, with %d slice edges in violation of dependency constraints.".format(g.allNodes.size, violations.size))
    }
  }

}