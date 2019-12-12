class ChatConsole: ChatHistoryObserver {
    override fun messageUpdate(message: ChatMessage) {
        ChatHistory.printConsole()
    }
}