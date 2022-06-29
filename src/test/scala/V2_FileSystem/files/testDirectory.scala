package V2_FileSystem.files
import org.scalatest.{Matchers, WordSpec}

class testDirectory extends WordSpec with Matchers {

  /*
  Manually create                       /
                                      a       aa
                                   bb     b
                                        cc  c
                                           dd d
   */
  /*
   "A Person" should {
    "be instantialed with an age and name" in {
      val john = Person(firstName="John",lastName="Smith",42)
      john.firstName should be("John")
      john.lastName should be("Smith")
      john.age should be(42)
    }
  }
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

  "hasEntry(name:String)检查当前目录是否有名字叫name的子目录，如果有返回true，否则就是false" should{
    "/a/b should contains c folder"in{
      bDirectory.hasEntry("c") should be(true)
    }
    "/a/b/c/d doesn't have e folder"in{
      dDirectory.hasEntry("e") should be(false)
    }
  }

  "hasEntryInDescendant(name:String)检查当前目录以及当前目录以下的所有的子目录是否包含名字叫name的目录，如果有返回true,否则false" should{
    "/a should find dd folder" in{
      aDirectory.hasEntryInDescendant("dd") should be(true)
    }
  }

  "hasEntryInDescendant(parentPath:String,name:String) " +
    "/parentPath/name路径是否存在，如果有返回true,否则false。一般是root来调用" should{
    "/a/b/c should exist" in{
      root.hasEntryInDescendant("/a/b","c") should be(true)
    }
    "/a/b/c/cc shouldn't exist" in{
      root.hasEntryInDescendant("/a/b/c","cc") should be(false)
    }
  }

  "returnAllEntryInDescendant将返回List[Directory]，包括当前目录以及底下的所有目录" should{
    "/a 将包含bb a cc b d c dd这些目录" in{
      aDirectory.returnAllEntryInDescendant().map(_.name) should be(List("bb", "a", "cc", "b", "d", "c", "dd"))
    }
  }

  "findEntryInDescendant(name:String)将返回Directory，所以运行这个method之前要先运行hasEntryInDescendant，" +
    "保证确保存在名叫name的目录，然后才运行这个method。" +
    "这个method将在当前目录以及底下的所有的目录中检查是否有叫name的目录，然后返回对应的Directory" should{
    "should dispaly /a/b/cc" in{
      root.findEntryInDescendant("cc").path should be("/a/b/cc")
    }
    "should display /a/b/c" in{
      root.findEntryInDescendant("c").path should be("/a/b/c")
    }
  }

  "findEntryInDescendant(parentPath:String,name:String)将返回Directory，所以运行这个method之前要先运行hasEntryInDescendant，" +
    "保证确保存在/parentPath/name，然后才运行这个method。" +
    "这个method将返回/parentPath/name对应的Directory。一般由root调用" should{
    "should dispaly /a/b/c/dd" in{
      root.findEntryInDescendant("/a/b/c","dd").path should be("/a/b/c/dd")
    }
    "should display /a/b/c" in{
      root.findEntryInDescendant(parentPath="/a/b",name="c").path should be("/a/b/c")
    }
  }

}