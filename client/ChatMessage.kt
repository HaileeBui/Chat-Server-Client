package com.example.chatserverngb
import kotlinx.serialization.Serializable


@Serializable
class ChatMessage (val command : String, val username: String, val text: String) {
    override fun toString(): String{
        return "$username: $text"
    }
}