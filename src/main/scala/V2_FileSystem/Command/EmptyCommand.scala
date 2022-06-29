package V2_FileSystem.Command

import V2_FileSystem.FileSystem.State

class EmptyCommand extends Command {
  override def apply(state: State): State = state.setMessage("Empty command!")
}
