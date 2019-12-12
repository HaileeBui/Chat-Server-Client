 package com.example.chatserverngb

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_register.view.*
import kotlinx.android.synthetic.main.message_layout.*


 class MainActivity : AppCompatActivity(),ChatHistoryObserver{
     val messageList = ChatHistory.chatHistory
     lateinit var adapter: MyRecyclerViewAdapter

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread{ChatServerConnector.runConnector()}.start() //start connecting to server and receiving message
        ChatHistory.registerObserver(this)

         val register = findViewById(R.id.registerButton) as Button
         register.setOnClickListener{  //open dialogView when pressing button
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_register,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Log in to start chat")

            val mAlertDialog = mBuilder.show()
            mDialogView.register.setOnClickListener { //log in button to save username and return to chat
                val name = mDialogView.editTxtName.text.toString()
                if (name.length > 0){
                    if (Users.checkUsername(name)) {
                        Users.add(name)
                        ChatServerConnector.sendUsername(name)
                        mAlertDialog.dismiss()
                    } else
                        Toast.makeText(
                            applicationContext, "Username has been taken.",
                            Toast.LENGTH_SHORT).show()
                }else
                    Toast.makeText(applicationContext,"Choose name to chat.",
                    Toast.LENGTH_SHORT).show()
            }
        }

         recyclerView.layoutManager = LinearLayoutManager(this)
         adapter = MyRecyclerViewAdapter(this, messageList)
         recyclerView.adapter = adapter

         val send = findViewById(R.id.sendButton) as ImageButton
        send.setOnClickListener {  //send button which push messages into server
            val insertedMessage = findViewById(R.id.chatbox) as EditText
            val message = insertedMessage.text.toString()
            if(message.length>0) {
                Thread { ChatServerConnector.sendMessage(message) }.start() // create a thread for each send
                chatbox.text.clear()
                Log.d("MainActivity", "sendButton")
            }
            else
                Toast.makeText(applicationContext,"Please write sth!",
                    Toast.LENGTH_SHORT).show()
        }
     }

    override fun messageUpdate(message: ChatMessage) {
        Log.d("main","update")
        runOnUiThread(){
            adapter.notifyDataSetChanged()
        }

    }
 }

 class MyRecyclerViewAdapter (private val context: Context, private val messageList : MutableList<ChatMessage>):
 RecyclerView.Adapter<MyViewHolder>() {
     override fun onCreateViewHolder(vg: ViewGroup, vt: Int): MyViewHolder {
         val itemView =
             LayoutInflater.from(context).inflate(
                 R.layout.message_layout, vg,
                 false
             )
         return MyViewHolder(itemView)
     }

     override fun getItemCount(): Int {
         Log.d("main","${messageList.size}")
         return messageList.size
     }

     override fun onBindViewHolder(vh: MyViewHolder, pos: Int) {
         val item = messageList[pos]
         vh.itemView.findViewById<TextView>(R.id.username).text = item.username
         vh.itemView.findViewById<TextView>(R.id.message).text = item.text
     }
 }

  class MyViewHolder(itemView: View):
     RecyclerView.ViewHolder(itemView) {
     internal var message: TextView
     internal var name : TextView
     init{
         message =itemView.findViewById(R.id.message)as TextView
         name = itemView.findViewById(R.id.username) as TextView
     }
  }


