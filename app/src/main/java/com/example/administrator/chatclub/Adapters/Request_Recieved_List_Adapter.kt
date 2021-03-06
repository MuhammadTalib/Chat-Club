package com.example.administrator.chatclub.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.administrator.chatclub.ViewHolders.AddChatListViewHolder
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.Models.usertempdata

class Request_Recieved_List_Adapter (var context:Context,var data:ArrayList<usertempdata>,
                                     val onItemClick:(Int)->Unit): RecyclerView.Adapter<AddChatListViewHolder>() {


    var temp=0
    override fun getItemCount(): Int =data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddChatListViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.add_chat_list_view,parent,false)
        return AddChatListViewHolder(itemView)
    }
    override fun onBindViewHolder(p0: AddChatListViewHolder, p1: Int)
    {
        p0.myText.text=data[p1].Username
        p0.myAddButton.setOnClickListener {
            onItemClick(p1)
        }
        if(data[p1].profilepic!=null) {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions())
                    .load(data[p1].profilepic).into(p0.ProfilePic)
        }

    }
    fun add(item: usertempdata){
        data.add(item)
        notifyItemInserted(data.size-1)
    }
    fun addAll(itemList:ArrayList<usertempdata>){
        data.addAll(itemList)
        notifyItemInserted(data.size-1)
    }



}
