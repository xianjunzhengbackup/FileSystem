package V2_FileSystem.Command
import V2_FileSystem.FileSystem.State
import V2_FileSystem.files.Directory
import org.scalatest.{Matchers, WordSpec}

class testMkdir extends WordSpec with Matchers{
  /*
  manually created folders for testing purpose
  /a
  /a/bb
  /a/b
  /a/b/cc
  /a/b/c
  /a/b/c/d
  /a/b/c/dd
   */

  val dDirectory=Directory.empty("/a/b/c","d")
  val ddDirectory=Directory.empty("/a/b/c","dd")

  "a Directory called d has been created and its path is /a/b/c/d" in{
    dDirectory.path should be("/a/b/c/d")
  }

  val cDirectory=Directory.create("/a/b","c",List(dDirectory,ddDirectory))
  val ccDirectory=Directory.empty("/a/b","cc")
  "a Directory called c has been created and its subfolder in parentPath should be a b c" in{
    cDirectory.getAllFoldersInPath should be(List("a","b","c"))
  }


  val bDirectory=Directory.create("/a","b",List(ccDirectory,cDirectory))
  val bbDirectory=Directory.empty("/a","bb")

  val aDirectory=Directory.create("","a",List(bbDirectory,bDirectory))
  "/a directory was created and its path should be /a" in{
    aDirectory.path should be("/a")
  }

  val aaDirectory=Directory.empty("","aa")
  val root = Directory.ROOT(List(aDirectory,aaDirectory))

  "doMkdir(state:State,str:String)将在state.wd的目录下创建名叫str的子目录，返回一个新的state"should{
    "mkdir f under /a/b/c"in{
      val state = new State(root,cDirectory,"mkdir")
      val mkdirCommand=new Mkdir("f")
      val newState = mkdirCommand.doMkdir(state)
      val newroot=newState.root

      newroot.returnAllEntryInDescendant().map(_.name) should be(List("aa", "", "bb", "a", "cc", "b", "d", "c", "dd", "f"))
      newroot.findEntryInDescendant(newState.wd.path,"f").path should be("/a/b/c/f")
      newState.output should be("f is created!")
    }
    "mkdir f under empty /" in{
      val newroot=Directory.empty("","")
      val state=new State(newroot,newroot,output="f")
      val mkdirCommand=new Mkdir("f")
      val newState=mkdirCommand.doMkdir(state)
      newState.root.findEntryInDescendant("","f").path should be("/f")
    }
  }

}
