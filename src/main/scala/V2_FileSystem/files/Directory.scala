package V2_FileSystem.files

class Directory(override val parentPath:String,override val name:String,val child:List[Directory])
  extends DirEntry(parentPath,name) {
  def findDescendant(path: List[String]):Directory = ???

  def getAllFoldersInPath :List[String]= {
    // /a/b/c/d =>List["a","b","c","d"]
    //substring就是为了消除第一个"/"
    val parentFolders=path.substring(1).split(Directory.SEPERATOR).toList
    parentFolders
  }
  def path :String=parentPath+Directory.SEPERATOR+name

  def hasEntry(name: String): Boolean = {
    //check under current folder /parpentPath/this.name contains $name folder
    val res =for(eachChild <- child if child.isEmpty==false) yield eachChild.name.equals(name)
    val orAll=res.foldLeft(false)(_ | _)
    orAll
  }

  def hasEntryInDescendant(name:String):Boolean={
    //check subfolders under current folder contains $name folder
    if(child.isEmpty) this.name.equals(name)
    else (for(eachChild <- child) yield (eachChild.hasEntryInDescendant(name) | this.name.equals(name))).foldLeft(false)(_ | _)
  }

  def hasEntryInDescendant(parentPath:String,name:String):Boolean={
    //check under parentPath/name
    val inCurrentPath= this.name.equals(name) & this.parentPath.equals(parentPath)
    if(child.isEmpty) inCurrentPath
    else (for(eachChild <- child) yield (eachChild.hasEntryInDescendant(parentPath,name) | inCurrentPath)).foldLeft(false)(_ | _)
  }

  def returnAllEntryInDescendant():List[Directory]={
    //find all the subfolders including self
    //includes repeated folders
    if(child.isEmpty) List(this)
    else (for(eachChild <- child) yield (eachChild.returnAllEntryInDescendant())++List(this)).flatten.distinct
  }

  def findEntryInDescendant(name:String):Directory= {
    //find Directories by folder's name
    val allEntries = returnAllEntryInDescendant()
    val res = for (entry <- allEntries if entry.name.equals(name)) yield entry
    res(0)
  }

  def findEntryInDescendant(parentPath:String,name:String):Directory={
    //find Directories by path(parentPath/name)
    val allEntries = returnAllEntryInDescendant()
    val res = for (entry <- allEntries if (entry.name.equals(name) & entry.parentPath.equals(parentPath))) yield entry
    res(0)
  }
  def updateForParent(root:Directory)={
    val parentName = parentPath.split("/")(-1)
    val parent = root.findEntryInDescendant(parentPath,name)
  }
}


object Directory{
  val SEPERATOR = "/"
  val ROOT_PATH="/"

  def empty(parentPath:String,name:String): Directory ={
    new Directory(parentPath,name,List())
  }
  def create(parentPath:String,name:String,child:List[Directory])=new Directory(parentPath,name,child)

  def ROOT: Directory=Directory.empty("","")

  def ROOT(child:List[Directory])=new Directory(parentPath = "",name="",child)
}