package V2_FileSystem.Command

import V2_FileSystem.FileSystem.State
import V2_FileSystem.files.Directory

class Mkdir(str:String) extends Command {

  def checkIllegal(str: String): Boolean = str.contains(".")

  def doMkdir(state: State, str :String =this.str): _root_.V2_FileSystem.FileSystem.State = {
    val wd=state.wd
    val root=state.root
    val parentNameFornewDirectory= if(wd.parentPath.equals("")) "" else wd.path
    /*
    假如是在wd是根目录，那么newDirectory的parentPath将是“”
    假如wd是其它目录，则可以吧wd.path作为newDirectory的parentPath
     */
    val newDirectory=Directory.empty(parentNameFornewDirectory,str)
    val newroot=newDirectory.updateForParent(root)
    val newwd=newroot.findEntryInDescendant(wd.parentPath,wd.name)
    val newState=new State(newroot,newwd,s"$str is created!")
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
