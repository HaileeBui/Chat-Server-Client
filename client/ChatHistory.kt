package com.example.chatserverngb

import android.util.Log

object ChatHistory : ChatHistoryObservable {
    val chatHistory = mutableListOf<ChatMessage>()

    val observers = mutableListOf<ChatHistoryObserver>()

    @Synchronized
    override fun insert (message: ChatMessage){   //insert message and notify observer
        chatHistory.add(message)
        observers.forEach{it.messageUpdate(message)}
        Log.d("ChatHistory", "update")
    }
    override fun toString(): String{
        var list = ""
        for ( i: ChatMessage in chatHistory) {
            list += "$i \n"
        }
        return list
    }

    fun printConsole(){      //print chat history to console
        var list =""
        for(i in chatHistory){
            list += "${i.username}: ${i.text}\n"
        }
        println("Chat console: ")
        println(list)
    }
    fun countChatMessage(username:String) :Int{ //print how many message individual user has
        var count = 0
        for(i in chatHistory) {
            if (i.username == username)
                count++
        }
        //println ("$username has $count messages")
        return count
    }
    override fun registerObserver(observer: ChatHistoryObserver){
        observers.add(observer )
    }

    override fun deregisterObserver(observer: ChatHistoryObserver) {
        observers.remove(observer)
    }
}