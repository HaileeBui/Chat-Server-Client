package com.example.chatserverngb

interface ChatHistoryObserver {
    fun messageUpdate(message : ChatMessage)

}