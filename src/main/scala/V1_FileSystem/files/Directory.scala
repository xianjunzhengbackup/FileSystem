package V1_FileSystem.files

class Directory(override val parentPath:String,override val name:String,val contents:List[DirEntry])
  extends DirEntry(parentPath,name) {

}

object Directory{
  val SEPERATOR = "/"
  val ROOT_PATH="/"

  def empty(parentPath:String,name:String): Directory ={
    new Directory(parentPath,name,List())
  }

  def ROOT: Directory=Directory.empty("","")
}