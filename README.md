# Chat-Server-Client


The project includes server part and client part. Server is built on IntelliJ IDEA with port 3000.
Client is built in Android Stuido with a socket connected to port 3000

In general, users have to register a username to start. In order to send message, user types into chatbox and press send.
 
Language: Kotlin </br>
Design pattern: Observer

_Register Button: User click button and open dialog where they can type username and press login. If username has been taken,
it is informed on the screen and user need to choose another name. </br>
_Chatbox as EditText: User types message here. </br>
_Send Button: If chatbox is empty, it will toast that message is needed in order to send. Otherwise, it creates a thread to send messagae to server and print out to screen.


Drawback:  </br>
_Username should be conducted on seperate activity, when username is registered then user can start chatting. </br>
_If user failes in registering, they chat under "anonymous" username.

Further implementation: </br>
_Create seperate login page </br> 
_Users can see chat history and active users in seperate activity  </br>
_Delete username when quitting
