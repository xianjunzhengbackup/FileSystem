package V1_FileSystem.Command

import V1_FileSystem.FileSystem.State
import V1_FileSystem.files.{DirEntry, Directory}

class Mkdir(str:String) extends Command {

  def checkIllegal(str: String): Boolean = str.contains(".")

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry):Directory = ???

  def doMkdir(state: State, name: String):State = {
    val wd = state.wd
    val fullPath=wd.path

    //1.all the directories in the full path
    val allDirsInPath=wd.getAllFoldersInPath


    //2.create new directory entry in the wd
    val newDir=Directory.empty(wd.path,name)
    //3.update the whole directory structure starting from the root
    //(the directory structures is IMMUTABLE)
    val newRoot = updateStructure(state.root,allDirsInPath,newDir)
    // 4.find new working directory INSTANCE given wd's full path, in the NEW dirctory structure
    val newWd=newRoot.findDescendant(allDirsInPath)
    State(newRoot,newWd)
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
