interface ChatHistoryObservable {
    fun registerObserver ( observer : ChatHistoryObserver)
    fun deregisterObserver( observer: ChatHistoryObserver)
    fun insert (message : ChatMessage)
}