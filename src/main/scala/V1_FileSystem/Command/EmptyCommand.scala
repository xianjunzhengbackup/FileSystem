package V1_FileSystem.Command

import V1_FileSystem.FileSystem.State

class EmptyCommand extends Command {
  override def apply(state: State): State = state.setMessage("Empty command!")
}
