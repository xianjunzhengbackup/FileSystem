package V2_FileSystem.Command

import V2_FileSystem.FileSystem.State
import V2_FileSystem.files.Directory

class Mkdir(str:String) extends Command {

  def checkIllegal(str: String): Boolean = str.contains(".")

  def doMkdir(state: State, str :String =this.str): _root_.V2_FileSystem.FileSystem.State = {
    val wd=state.wd
    val root=state.root
    val newDirectory=Directory.empty(wd.path,str)
    val newroot=newDirectory.updateForParent(root)
    val newState=new State(newroot,wd,s"$str is created!")
    newState
  }

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
