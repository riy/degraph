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

    def readStream(reader: ClassReader, name: String): Unit = {
      try {
        reader.accept(new GraphBuildingClassVisitor(g), 0)
      } catch {
        case e: Exception =>
          println("Something went wrong when analyzing " + name)
          println("For this class some or all dependencies will be missing.")
          println("This is most likely due to a bug in the JDK (no, really) or ASM,")
          println("see  https://github.com/schauder/degraph/issues/68 for details.")
          println("If the stacktrace below does not match what you see in the issue above,")
          println("please create a new issue and include the stacktrace.")
          e.printStackTrace()
      }
    }

    def analyze(f: File) = {
      if (f.getName.endsWith(".class")) {
        val reader = new ClassReader(new BufferedInputStream(new FileInputStream(f)))
        readStream(reader, f.getName)
      } else {
        val zipFile = new ZipFile(f)
        val entries = zipFile.entries()
        while (entries.hasMoreElements) {
          val e = entries.nextElement()
          if (e.getName.endsWith(".class")) {
            val reader = new ClassReader(zipFile.getInputStream(e))
            readStream(reader, e.getName)
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

