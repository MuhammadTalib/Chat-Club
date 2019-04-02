package com.example.administrator.chatclub.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.administrator.chatclub.ViewHolders.ChatListViewHolder
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.Models.usertempdata

class ChatListAdapter (val thiS: Context,
                       val data:ArrayList<usertempdata>,
                       val openMessageList:(Int)->Unit,
                       val openProfile:(Int)->Unit) : RecyclerView.Adapter<ChatListViewHolder>() {


    override fun getItemCount(): Int =data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.chat_list_view,parent,false)
        return ChatListViewHolder(itemView)
    }
    override fun onBindViewHolder(p0: ChatListViewHolder, p1: Int) {

        p0.myText.text=data[p1].Username
        p0.openChatButton.setOnClickListener {
            openMessageList(p1)
        }
        p0.itemView.setOnClickListener {
            openProfile(p1)
        }
        if(data[p1].profilepic!=null) {
            Glide.with(thiS).applyDefaultRequestOptions(RequestOptions())
                    .load(data[p1].profilepic).into(p0.Profilepic)
        }

    }
    fun add(item: usertempdata){
        data.add(item)
        notifyItemInserted(data.size)
    }
    fun addAll(item:ArrayList<usertempdata>){
        data.addAll(item)
        notifyItemInserted(data.size-1)
    }
}