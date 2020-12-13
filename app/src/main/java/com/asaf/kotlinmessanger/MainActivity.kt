package com.asaf.kotlinmessanger

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_register.setOnClickListener {
            regiserNewUser()
        }

        text_haveAccount.setOnClickListener {
            Log.e("MESSAGE", "TEXT CLICKED")
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent,0)
        }


        button_selectPhoto.setOnClickListener{
            val photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)
        }

    }

    private fun regiserNewUser (){
        val email = text_email.text.toString()
        val password = text_password.text.toString()

        if(email.isEmpty() || password.isEmpty() ){
            Toast.makeText(this, "Email or password not entered",
                    Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if( !it.isSuccessful) return@addOnCompleteListener

                    uploadUserImage                    ()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to register: ${it.message}",
                            Toast.LENGTH_LONG).show()
                }

    }

    var photoUri: Uri? =null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode== 0 && resultCode == Activity.RESULT_OK && data != null){
            photoUri =  data.data

            val bitmapPhoto = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
            circularImageView.setImageBitmap(bitmapPhoto)
            button_selectPhoto.alpha = 0f
//            val bitmapDrawable = BitmapDrawable(bitmapPhoto)
//            button_selectPhoto.setBackgroundDrawable(bitmapDrawable)
        }
    }


    private fun uploadUserImage() {
        if(photoUri == null ) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(photoUri!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        saveUserToDatabase(it.toString())
                    }
                }

    }

    private fun saveUserToDatabase(imageUrl:String) {
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User( text_username.text.toString(),imageUrl, uid )
        ref.setValue(user).addOnSuccessListener {
            Log.e("Message", "User Registered Successfully")
        }

    }

}

