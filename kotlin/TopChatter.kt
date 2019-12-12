class TopChatter:ChatHistoryObserver {
    override fun messageUpdate(message: ChatMessage) {
        println("Active users: ${Users.checkCount()}")
        Users.printToConsole()
    }
}