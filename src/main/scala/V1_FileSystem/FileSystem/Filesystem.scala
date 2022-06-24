package V1_FileSystem.FileSystem

import V1_FileSystem.Command.Command

import V1_FileSystem.files.Directory

import java.util.Scanner

object Filesystem extends App {

  val root=Directory.ROOT
  val state=State(root,root)
  val scanner = new Scanner(System.in)
  while (true) {
//
    state.show
    val input = scanner.nextLine()
    Command.from(input).apply(state)
  }

}
