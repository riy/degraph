package de.schauderhaft.degraph.analysis.asm

import java.io.File
import java.io.FileFilter
import java.io.FilenameFilter

class FileFinder(val rootPath: String) {
  def find(): Set[File] = {
    val root = new File(rootPath)
    if (root.isDirectory()) {
      val files = root.list(ClassFileFilter).toSet
      files.map(new File(root.getCanonicalPath(), _))
    } else
      Set()
  }
}

object ClassFileFilter extends FilenameFilter {
  override def accept(f: File, n: String) = n.endsWith(".class")
}