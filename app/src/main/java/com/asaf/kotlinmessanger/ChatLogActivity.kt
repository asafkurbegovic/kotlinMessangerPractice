package com.asaf.kotlinmessanger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.from_user_message_cloud_layout.view.*
import kotlinx.android.synthetic.main.to_user_message_cloud_layout.view.*

class ChatLogActivity : AppCompatActivity() {

    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val user = intent.getParcelableExtra<User>("USER_INFO")

        if (user != null) {
            supportActionBar?.title = user.username
        }

        messageListener()


        button_send.setOnClickListener {
            sendMessageHandler()
        }



        recyclerView_chatLog.adapter = adapter


    }

    private fun messageListener() {
        val reference = FirebaseDatabase.getInstance().getReference("/messages")

        reference.addChildEventListener( object :ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val messageInfo = snapshot.getValue(ChatMessage::class.java)
                if (messageInfo != null) {
                    if (messageInfo.formId == FirebaseAuth.getInstance().uid)
                        adapter.add(ChatItemTo(messageInfo.text))
                    else{
                        adapter.add(ChatItemFrom(messageInfo.text))
                    }
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    class ChatMessage (val messageId: String, val text:String, val formId: String, val toID:String, val timeStamp: Long){
        constructor(): this("","","","",-1)
    }

    private fun sendMessageHandler() {
        val rawText = textView_message_chatLog.text.toString()
        val user = intent.getParcelableExtra<User>("USER_INFO")
        val toid = user?.uid
        val fromid = FirebaseAuth.getInstance().uid
        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        if (fromid == null) return
        val rawMessage = toid?.let {
            ChatMessage(reference.key!!,rawText, fromid,
                it,System.currentTimeMillis()/1000)
        }

        reference.setValue(rawMessage)
            .addOnSuccessListener {
                Log.e("SendMessage", "hello world!!!")
            }
    }

    private fun dummydata() {
        val adapter = GroupAdapter<GroupieViewHolder>()

//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
//
//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
//        adapter.add(ChatItemTo())
//        adapter.add(ChatItemFrom())
        recyclerView_chatLog.adapter = adapter
    }
}

class ChatItemFrom ( val text:String): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_fromRow.text = text
    }

    override fun getLayout(): Int {
        return R.layout.from_user_message_cloud_layout
    }

}

class ChatItemTo( val text: String): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_toRow.text = text
    }

    override fun getLayout(): Int {
        return R.layout.to_user_message_cloud_layout
    }

}