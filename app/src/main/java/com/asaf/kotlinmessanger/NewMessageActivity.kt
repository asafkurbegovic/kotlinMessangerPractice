package com.asaf.kotlinmessanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_layout.view.*

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title = "Select User"

        val adapter = GroupAdapter<GroupieViewHolder>()
        recyclerView_newMessage.adapter = adapter


        listAllUsers()
    }

    private fun listAllUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }
                recyclerView_newMessage.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}

class UserItem(val user: User) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    viewHolder.itemView.textView_username_userRow.text = user.username
        Picasso.get().load(user.profilePicUrl).into(viewHolder.itemView.imageView_profilePic_userRow)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_layout
    }

}