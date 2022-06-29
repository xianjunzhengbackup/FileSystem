package V1_FileSystem

import V1_FileSystem.files.Directory

object TestDirectory extends App {
  val testDirectory=new Directory("/a/b/c","d",List())
  println(testDirectory.getAllFoldersInPath)
}
