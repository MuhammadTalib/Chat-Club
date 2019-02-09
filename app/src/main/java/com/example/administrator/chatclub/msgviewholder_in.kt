package com.example.administrator.chatclub

import android.content.Context
import android.graphics.Bitmap
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView

open class MsgViewHolderIn(myItem: View) : MessageViewHolder(myItem){
  val userNameTextView: TextView = itemView.findViewById(R.id.userNameTv)

  override fun bindItem(msgItem: com.example.administrator.chatclub.Message) {
    super.bindItem(msgItem)
    userNameTextView.text = "10:00 P.M"

  }

}