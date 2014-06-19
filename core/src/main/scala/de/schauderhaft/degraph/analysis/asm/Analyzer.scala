package de.schauderhaft.degraph.analysis.asm

import de.schauderhaft.degraph.analysis.{NoSelfReference, AnalyzerLike}
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.graph.Graph
import org.objectweb.asm._
import java.io.{File, BufferedInputStream, FileInputStream}
import java.util.zip.ZipFile

object Analyzer extends AnalyzerLike {

  def analyze(sourceFolder: String, categorizer: (Node) => Node, filter: (Node) => Boolean): Graph = {
    val g = new Graph(categorizer, filter, new NoSelfReference(categorizer))

    def analyze(f : File)={
      if (f.getName.endsWith(".class")) {
        val reader = new ClassReader(new BufferedInputStream(new FileInputStream(f)))
        reader.accept(new GraphBuildingClassVisitor(g), 0)
      } else {
        val zipFile = new ZipFile(f)
        val entries = zipFile.entries()
        while (entries.hasMoreElements) {
          val e = entries.nextElement()
          if (e.getName.endsWith(".class")) {
            val reader = new ClassReader(zipFile.getInputStream(e))
            reader.accept(new GraphBuildingClassVisitor(g), 0)
          }
        }
      }
    }

    for (folder <- sourceFolder.split(System.getProperty("path.separator"))) {
      val fileFinder = new FileFinder(folder)
      val files = fileFinder.find()
      for {f <- files} {
        analyze(f)
      }
    }

    g
  }


}

