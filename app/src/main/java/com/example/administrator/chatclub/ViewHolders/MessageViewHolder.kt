package com.example.administrator.chatclub.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.example.administrator.chatclub.Models.Message
import com.example.administrator.chatclub.R

open class MessageViewHolder(myItem: View) : RecyclerView.ViewHolder(myItem) {
    val msgMsgTextView: TextView = itemView.findViewById(R.id.myMsgTv)
    val msgMsgImageView: ImageView = itemView.findViewById(R.id.myMessageImage)
    val msgMsgVideoView: VideoView = itemView.findViewById(R.id.myMessageVideo)

    open fun bindItem(msgItem: Message)
    {
        msgMsgTextView.text=msgItem.messageText

        if (msgItem.image!=null)
        {
            msgMsgImageView.visibility=View.VISIBLE
        }
    }
}