package com.example.ishaqhassan.androidchatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var msgsRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        msgsRef = FirebaseDatabase.getInstance().reference.child("messages")

        val msgs:ArrayList<ChatMessagesAdapter.Message> = arrayListOf()
        val msgsAdapter = ChatMessagesAdapter(msgs)
        chatMessagesList.adapter = msgsAdapter
        chatMessagesList.layoutManager = LinearLayoutManager(this)

        msgsRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                val msg:ChatMessagesAdapter.Message?  = dataSnapshot?.getValue(ChatMessagesAdapter.Message::class.java)
                if(msg !== null){
                    msgs.add(msg)
                    msgsAdapter.notifyDataSetChanged()
                    chatMessagesList.scrollToPosition(msgs.size-1)
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }

        })

        sendBtn.setOnClickListener({
            val user:FirebaseUser? = FirebaseAuth.getInstance().currentUser
            if(user != null){
               sendMsg(ChatMessagesAdapter.Message(msgEt.text.toString(),user.uid,user.email!!))
            }
        })
    }

    fun sendMsg(msg:ChatMessagesAdapter.Message){
        msgsRef.push().setValue(msg)
        msgEt.setText("")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.logoutBtn -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        return true
    }
}
