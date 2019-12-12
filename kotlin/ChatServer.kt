import java.net.ServerSocket

class ChatServer {
    fun serve(){
        try {
            val ss = ServerSocket(3000, 2)
            println("We have port " + ss.localPort)
            //ChatHistory.registerObserver(ChatConsole())  //register chatconsole as observer
            //ChatHistory.registerObserver(TopChatter())   //register topchatter as observer
            while (true) {
                val s = ss.accept()
                println("Server accepted")
                Thread(ChatConnector(s.getInputStream(), s.getOutputStream())).start()
            }
        }
        catch (error : Exception) {  //catch every exception and print error
            println("$error")
        }
        finally {
            println("All done")
        }
    }
}

