package com.asaf.kotlinmessanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title="Select User"
    }
}