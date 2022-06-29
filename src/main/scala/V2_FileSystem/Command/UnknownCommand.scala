package V2_FileSystem.Command

import V2_FileSystem.FileSystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State = state.setMessage("Command not found")
}
