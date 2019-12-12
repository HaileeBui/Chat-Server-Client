object Users {
    val users = HashSet<String>()
    var count = 0                           //count number of users in server

    fun remove(username: String){           //remove user
        if(users.contains(username)) {
            users.remove(username)
            count--
        }
    }

    fun add(username: String){              //add user
        if(!users.contains(username)){
            users.add(username)
            count++
        }
    }

    fun checkUsername(username: String): Boolean{   //return true if username is available
        return !users.contains(username)
    }

    override fun toString(): String {      //return list of users
        var list :String = ""
        for ( i in users) {
            list += "$i \n "
        }
        return list
    }
    fun checkCount(): Int{     //check how many users on server
        return count
    }
    fun printToConsole(){       //print how many messages each user has to console
        for(i in users){
            println("$i has ${ChatHistory.countChatMessage(i)} messages")
        }
    }

     /*
     fun topChatter(){
        var map = mutableMapOf<String,Int>()
        for(i in users){
            val c =ChatHistory.countChatMessage(i)
            map[i] = c
        }
        val sorted = map.toList().sortedByDescending { (_, value) -> value}.toMap()
        var count = 0
        while(count<2){
            for((k,v) in sorted){
                println("$k has $v messages")
                count++
            }
        }
    }*/
}