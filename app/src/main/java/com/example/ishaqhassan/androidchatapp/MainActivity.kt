package com.example.ishaqhassan.androidchatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            gotoChat()
        }

        signInBtn.setOnClickListener({
            signIn(emailEt.text.toString(),passEt.text.toString())
        })

        signUpBtn.setOnClickListener({
            signUp(emailEt.text.toString(),passEt.text.toString())
        })


    }

    private fun signIn(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener({
                    if(it.isSuccessful){
                        gotoChat()
                    }else{
                        Toast.makeText(this, "Error : ${it.exception?.message}", Toast.LENGTH_SHORT).show();
                    }
                })
    }

    private fun signUp(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener({task ->
                    if(task.isSuccessful){
                        gotoChat()
                    }else{
                        Toast.makeText(this, "Error : ${task.exception?.message}", Toast.LENGTH_SHORT).show();
                    }
                })
    }

    private fun gotoChat(){
        startActivity(Intent(this,ChatActivity::class.java))
        finish()
    }

}
