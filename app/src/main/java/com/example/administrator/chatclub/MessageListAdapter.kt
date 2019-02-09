package com.example.administrator.chatclub

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

class MessageListAdapter(val msgs:ArrayList<Message>) : RecyclerView.Adapter<MessageViewHolder>() {
    val MSG_IN = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.msg_item_in,parent,false)
        return MessageViewHolder(itemView)
    }

    override fun getItemCount(): Int = msgs.size

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int)
    {
        viewHolder.bindItem(msgs[position])
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }
}