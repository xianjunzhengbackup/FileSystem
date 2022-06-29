package V1_FileSystem.files

class Directory(override val parentPath:String,override val name:String,val contents:List[DirEntry])
  extends DirEntry(parentPath,name) {
  def findDescendant(path: List[String]):Directory = ???

  def getAllFoldersInPath :List[String]= {
    // /a/b/c/d =>List["a","b","c","d"]
    //substring就是为了消除第一个"/"
    val parentFolders=parentPath.substring(1).split(Directory.SEPERATOR).toList
    parentFolders
  }

  def path :String=parentPath+Directory.SEPERATOR+name

  def hasEntry(str: String): Boolean = false

}

object Directory{
  val SEPERATOR = "/"
  val ROOT_PATH="/"

  def empty(parentPath:String,name:String): Directory ={
    new Directory(parentPath,name,List())
  }

  def ROOT: Directory=Directory.empty("","")
}