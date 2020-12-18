package com.asaf.kotlinmessanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val user = intent.getParcelableExtra<User>("USER_INFO")

        if (user != null) {
            supportActionBar?.title = user.username
        }

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())

        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())
        adapter.add(ChatItemTo())
        adapter.add(ChatItemFrom())


        recyclerView_chatLog.adapter = adapter

    }
}

class ChatItemFrom: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.from_user_message_cloud_layout
    }

}

class ChatItemTo: Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.to_user_message_cloud_layout
    }

}