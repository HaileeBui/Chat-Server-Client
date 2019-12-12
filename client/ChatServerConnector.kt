package com.example.chatserverngb

import android.util.Log
import kotlinx.serialization.json.Json
import java.io.OutputStream
import java.io.PrintStream
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import java.util.stream.Stream

object ChatServerConnector: ChatHistoryObservable {
    val ip= "10.0.2.2"
    val port = 3000
    lateinit var socket :Socket
    lateinit var ins: Scanner
    lateinit var output: PrintStream
    var username ="anonymous"

    fun runConnector(){
        try{
        socket = Socket(ip,port)
        output = PrintStream(socket.getOutputStream(),true)
        ins = Scanner(socket.getInputStream())

        var connected : Boolean = true
        while (connected) {
            Log.d("connector", "here")
            var input: String = ins.nextLine()
            Log.d("connector", "$input")
            var newMessage = Json.parse(ChatMessage.serializer(), input)
            Log.d("connector", "after message")
            ChatHistory.insert(newMessage)

        }
        }catch (er: Error){
            println(er)
        }

    }

    fun sendMessage(input:String){  //send message to server
        var message = ChatMessage("send", username,input)
        serverPrint(message)
    }

    fun serverPrint(chatMessage: ChatMessage){ //transfer Json message to string and print out
        Log.d("serverprint", Json.stringify(ChatMessage.serializer(),chatMessage))
        Thread{
            val newMessage=Json.stringify(ChatMessage.serializer(),chatMessage)
            output.println(newMessage)
        }.start()
    }

    fun sendUsername(inputName: String){
        username=inputName
        val message = ChatMessage("username",username,"")
        serverPrint(message)
    }

    override fun registerObserver(observer: ChatHistoryObserver) {
        ChatHistory.registerObserver(observer)
    }

    override fun deregisterObserver(observer: ChatHistoryObserver) {
        ChatHistory.deregisterObserver(observer)
    }

    override fun insert(message: ChatMessage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}


