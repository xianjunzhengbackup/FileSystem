val seq1 = Seq(11,2,3).toList
val seq2=Seq(4,51,6).toList
val seq3=Seq(7,81,9).toList
val list=Seq(seq1,seq2,seq3).toList
println(list)
println(list.flatten)
list ++ seq1
val ilist=for(i <- 1 to 10) yield i
val iilist=ilist.toList ++ List(45)
val nameList="/a/b/c".split("/")
nameList.isEmpty
val name=nameList(nameList.length-1)
val newnameList=nameList.patch(nameList.length-1,Nil,1)
var newname =""
newnameList.foreach(newname += "/"+_)
newname.substring(1)

val newList=list.flatten.zipWithIndex

