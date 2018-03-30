package com.example.ishaqhassan.androidchatapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ChatMessagesAdapter(private val messages:ArrayList<Message>) : RecyclerView.Adapter<ChatMessagesAdapter.MyChatMsgViewHolder>() {

    data class Message(val msgText:String = "",val uid:String = "",val uname:String = ""){
        constructor() : this("")
    }

    class MyChatMsgViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        private val msgTextTv: TextView = itemView.findViewById(R.id.msgTextTv)

        fun bindMsg(msg:Message){
            msgTextTv.text = msg.msgText
        }

    }

    private val IN_MSG:Int = 1
    private val OUT_MSG:Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChatMsgViewHolder {
        val inMsgView = LayoutInflater.from(parent.context).inflate(R.layout.in_msg_layout,parent,false)
        val outMsgView = LayoutInflater.from(parent.context).inflate(R.layout.out_msg_layout,parent,false)
        return when(viewType){
            IN_MSG -> MyChatMsgViewHolder(inMsgView)
            OUT_MSG -> MyChatMsgViewHolder(outMsgView)
            else -> MyChatMsgViewHolder(outMsgView)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MyChatMsgViewHolder, position: Int) {
        holder.bindMsg(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        val msg:Message = messages[position]
        return when(FirebaseAuth.getInstance().currentUser?.uid){
            msg.uid -> OUT_MSG
            else -> IN_MSG
        }
    }

}