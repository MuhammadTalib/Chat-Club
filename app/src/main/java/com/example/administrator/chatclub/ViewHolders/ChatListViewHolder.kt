package com.example.administrator.chatclub.ViewHolders

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import com.example.administrator.chatclub.R

class ChatListViewHolder(MyItem: View): RecyclerView.ViewHolder(MyItem)
{
   // val myImage: ImageView =MyItem.findViewById(R.id.image)
    val myText: TextView =MyItem.findViewById(R.id.name)
   // val myStatus: TextView =MyItem.findViewById(R.id.status)
    val openChatButton:ImageView=MyItem.findViewById(R.id.openchatbtn)
}