package com.example.chatserverngb

interface ChatHistoryObservable {
    fun registerObserver ( observer : ChatHistoryObserver)
    fun deregisterObserver( observer: ChatHistoryObserver)
    fun insert (message : ChatMessage)
}