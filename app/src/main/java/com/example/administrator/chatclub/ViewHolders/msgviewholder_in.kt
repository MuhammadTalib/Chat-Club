package com.example.administrator.chatclub.ViewHolders

import android.view.View
import android.widget.TextView
import com.example.administrator.chatclub.Models.Message
import com.example.administrator.chatclub.R

open class MsgViewHolderIn(myItem: View) : MessageViewHolder(myItem){
    val userNameTextView: TextView = itemView.findViewById(R.id.userNameTv)

    override fun bindItem(msgItem: Message) {
        super.bindItem(msgItem)
        userNameTextView.text = msgItem.userName

    }

}