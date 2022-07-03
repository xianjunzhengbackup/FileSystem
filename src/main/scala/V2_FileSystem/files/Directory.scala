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

  def infoParent():List[String]={
    //找到parent的parnetPath以及parent名字，为下面updateForParent服务
    //它将返回一个list，第一个String将是Grandpa path，第二个String将是parent's name
    //假如parentPath是/a/b/c
    var newPath=""
    if(parentPath.isEmpty) List("","")
    else {
      val nameList=parentPath.split("/") //["",a,b,c]
      val name = nameList(nameList.length-1)  // c
      val newnameList=nameList.patch(nameList.length-1,Nil,1) //["",a,b]
      newnameList.foreach(newPath += "/"+_)  //newPath://a/b
      newPath=newPath.substring(1) //   /a/b
      List(newPath,name)  //[c,/a/b]
    }


  }
  def updateForParent(root:Directory) : Directory={
    //1.找到自个的父亲 clone一个新父亲出来
    // 2.在新父亲的child list中找到跟自己同名的，删掉它，把自己加入这个child list中
    //3. 递归调用新父亲的updateForParent，直到递归到root，然后返回这个新root
    val List(path,name) = if(parentPath.equals("") | parentPath.equals("/")) List("","") else infoParent()
    val parent= if(parentPath.equals("")) root else root.findEntryInDescendant(parentPath=path, name=name)
    val removeOldSelf=for(each <- parent.child if each.name.equals(this.name)==false) yield each
    val newChildList = removeOldSelf.toList ++ List(this)
    val newParent=new Directory(parent.parentPath,parent.name,newChildList)
    if(parentPath.equals("")) newParent
    else newParent.updateForParent(root)
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