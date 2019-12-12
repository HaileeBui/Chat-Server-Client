import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

class ChatConnector (val streamIns: InputStream, val streamOut: OutputStream): Runnable, ChatHistoryObserver{
    val ins: Scanner = Scanner(streamIns)
    val out: PrintStream = PrintStream(streamOut, true)
    var username : String = ""
    var input : String =""


    override fun messageUpdate(message: ChatMessage) {
        out.println(Json.stringify(ChatMessage.serializer(),message)) //print out with json
        out.flush()
    }
    override fun run() {
        ChatHistory.registerObserver(this)

        while (true) {
            input = ins.nextLine()
            println(input)
            var newMessage = Json.parse(ChatMessage.serializer(),input)  //create new message object with json format

            if(newMessage.command == "send")
                ChatHistory.insert(newMessage)
            else if (newMessage.command == "history")
                out.println(ChatHistory.toString())
            else if (newMessage.command == "users")
                out.println(Users.toString())
            else if (newMessage.command == "quit")
                quit()
            else if(newMessage.command =="username"){
                var registeredUsername = newMessage.username
                if (Users.checkUsername(registeredUsername)) {
                    username = registeredUsername
                    Users.add(username)
                }
            }
        }
    }
    private fun quit(){
        Users.remove(username)
        ins.close()
    }

}