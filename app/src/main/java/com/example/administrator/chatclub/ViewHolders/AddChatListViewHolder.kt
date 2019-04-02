package com.example.administrator.chatclub.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.administrator.chatclub.R

class AddChatListViewHolder(MyItem: View): RecyclerView.ViewHolder(MyItem)
{
    val myText: TextView =MyItem.findViewById(R.id.name)
    val myStatus: TextView =MyItem.findViewById(R.id.status)
    val myAddButton:ImageView=MyItem.findViewById(R.id.add)
    val ProfilePic:ImageView=MyItem.findViewById(R.id.profileimage)
}