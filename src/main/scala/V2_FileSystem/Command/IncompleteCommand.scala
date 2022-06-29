package V2_FileSystem.Command

import V2_FileSystem.FileSystem.State

class IncompleteCommand(str:String) extends Command {
  override def apply(state: State): State = state.setMessage(s"$str is not completed")
}
