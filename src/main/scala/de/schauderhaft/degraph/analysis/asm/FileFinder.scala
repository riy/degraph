package de.schauderhaft.degraph.analysis.asm

import java.io.{FileFilter, File, FilenameFilter}

class FileFinder(val rootPath: String) {
  private def singleDirFind(root: File): Set[File] = {
    if (root.isDirectory()) {
      val files = root.list(ClassFileFilter).toSet
      files.map(new File(root.getCanonicalPath(), _)) ++ expandDirs(root.listFiles(DirFilter).toSet)
    } else
      Set(root.getAbsoluteFile).filter(
        (f: File) => ClassFileFilter.accept(f.getParentFile, f.getName)
      )
  }

  private def expandDirs(files: Set[File]) =
    files.flatMap(singleDirFind(_))

  def find(): Set[File] =
    singleDirFind(new File(rootPath))
}

object ClassFileFilter extends FilenameFilter {
  override def accept(f: File, n: String) =
    n.endsWith(".class") ||
      n.endsWith(".jar")
}

object DirFilter extends FileFilter {
  def accept(pathname: File): Boolean = pathname.isDirectory
}