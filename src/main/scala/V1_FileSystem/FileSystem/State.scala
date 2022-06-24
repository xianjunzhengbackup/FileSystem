package V1_FileSystem.FileSystem

import V1_FileSystem.files.Directory

class State(val root:Directory,val wd:Directory,val output:String) {
  def show: Unit= {
    print(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message:String):State= {
    println(message)
    State(root,wd,message)

  }
}

object State{
  val SHELL_TOKEN = "$ "

  def apply(root:Directory,wd:Directory,output:String=""):State=
    new State(root, wd, output)
}
