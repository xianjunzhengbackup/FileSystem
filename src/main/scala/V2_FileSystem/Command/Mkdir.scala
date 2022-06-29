package V2_FileSystem.Command

import V2_FileSystem.FileSystem.State
import V2_FileSystem.files.Directory

class Mkdir(str:String) extends Command {

  def checkIllegal(str: String): Boolean = str.contains(".")

  def doMkdir(state: State, str: String): _root_.V2_FileSystem.FileSystem.State = ???

  override def apply(state: State): State = {
    val wd=state.wd
    if(wd.hasEntry(str)){
      state.setMessage("Entry "+str+" already existed!")
    } else if(str.contains(Directory.SEPERATOR))
      state.setMessage(Directory.SEPERATOR + " isn't allowed")
      else if(checkIllegal(str))
        state.setMessage(str+": illegal entry name")
    else{
        doMkdir(state,str)
      }
    }
  }
