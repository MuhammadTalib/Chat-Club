package com.example.administrator.chatclub.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.chatclub.ViewHolders.AddChatListViewHolder
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.Models.Users

class Request_Recieved_List_Adapter (val data:ArrayList<Users>,
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
            Log.e("hahaha","${p1}")
            onItemClick(p1)

        }

    }
    fun add(item: Users){
        data.add(item)
        notifyItemInserted(data.size-1)
    }



}
