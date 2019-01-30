package com.example.administrator.chatclub

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

class ChatListAdapter ( val data:ArrayList<UserAccount>,
                          val openMessageList:(Int)->Unit ): RecyclerView.Adapter<ChatListViewHolder>() {


    override fun getItemCount(): Int =data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.chat_list_view,parent,false)
        return ChatListViewHolder(itemView)
    }
    override fun onBindViewHolder(p0: ChatListViewHolder, p1: Int) {

        if(data[p1].userImage is Int)
            p0.myImage.setImageResource(data[p1].userImage as Int)
        else if(data[p1].userImage is Bitmap)
            p0.myImage.setImageBitmap(data[p1].userImage as Bitmap)

        p0.myText.text=data[p1].Username
        p0.myStatus.text=data[p1].lastmessage
        p0.itemView.setOnClickListener {
            openMessageList(p1)
            Log.e("p1","$p1")
        }

    }
}