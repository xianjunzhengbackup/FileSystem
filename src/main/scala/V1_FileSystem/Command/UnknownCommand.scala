package V1_FileSystem.Command

import V1_FileSystem.FileSystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State = state.setMessage("Command not found")
}
