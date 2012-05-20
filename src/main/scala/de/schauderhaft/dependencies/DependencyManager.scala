package de.schauderhaft.dependencies
import de.schauderhaft.dependencies.analysis.Analyzer
import de.schauderhaft.dependencies.writer.Writer
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter
import writer.WriterTest
import scala.xml.XML

object DependencyManager {

    def main(args : Array[String]) : Unit = {
        val g = Analyzer.analyze("./lib/junit-4.8.2.jar")

        val xml = (new Writer()).toXml(g)
        XML.save("exampleX.graphml", xml, "UTF8", true, null)
    }

}