package de.schauderhaft.degraph.analysis.asm

import java.io.File

class FileFinder(val rootPath: String) {
  def find(): Set[File] = {
    val root = new File(rootPath)
    if (root.isDirectory()) {
      val files = root.list().toSet
      files.map(new File(root.getCanonicalPath(), _))
    } else
      Set()
  }
}