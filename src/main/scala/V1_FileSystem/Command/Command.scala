package V1_FileSystem.Command

import V1_FileSystem.FileSystem.State

trait Command {
  def apply(state:State):State
}

object Command{
  def from(input:String):Command=
    new UnknownCommand
}