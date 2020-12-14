package com.asaf.kotlinmessanger

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)


        signedInVerification()

    }

    fun signedInVerification() {
        val uid = FirebaseAuth.getInstance().uid

        if (uid == null) {
            val notSignedIntent = Intent(this, MainActivity::class.java)
            notSignedIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(notSignedIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_SignOut -> {
                FirebaseAuth.getInstance().signOut()
                val signOutIntent = Intent(this, LoginActivity::class.java)
                signOutIntent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(signOutIntent)
            }
            R.id.menu_newMessage -> {
                val newMessageIntent = Intent(this, NewMessageActivity::class.java)
                startActivity(newMessageIntent)

            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

}