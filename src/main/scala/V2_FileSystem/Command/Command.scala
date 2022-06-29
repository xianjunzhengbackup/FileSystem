package V2_FileSystem.Command

import V2_FileSystem.FileSystem.State

trait Command {
  def apply(state:State):State
}

object Command{
  val MKDIR="mkdir"
//  def emptyCommand:Command = new EmptyCommand
//  def incompleteCommand(str: String) :Command = new IncompleteCommand(str)
  /*
  Instead of creating new classes like emptyCommand and incompleteCommand, using generic class is much more easy!
   */
def emptyCommand:Command = new Command {
  override def apply(state: State): State = state.setMessage("Empty command!")
}
  def incompleteCommand(str: String) :Command = new Command {
    override def apply(state: State): State = state.setMessage(s"$str is incomplete command!")
  }
  def from(input:String):Command={
    val tokens = input.split(" ")
    if(tokens.isEmpty || input.isEmpty) emptyCommand
    else if(MKDIR.equals(tokens(0))){
      if(tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    } else new UnknownCommand
  }
}