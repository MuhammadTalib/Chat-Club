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
    /*   Log.e("binding",msgItem.msgText)
        if(msgItem.msgText!="")
        {
            Log.e("spac","is space")
            if(msgItem.msgText!!.length < 22)
            {
                val params = msgMsgTextView.getLayoutParams()
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                msgMsgTextView.setLayoutParams(params)

            }
            msgMsgTextView.visibility=View.VISIBLE
            msgMsgTextView.text = msgItem.msgText
        }
        if(msgItem.ImagedMessage==1)
        {
            if(msgItem.message_image.toString().contains("image"))
            {
                msgMsgImageView.visibility=View.VISIBLE
                msgMsgImageView.setImageURI(msgItem.message_image as Uri)

            }
            else if(msgItem.message_image.toString().contains("video"))
            {
                msgMsgVideoView.visibility=View.VISIBLE
                msgMsgVideoView.setVideoURI(msgItem.message_image as Uri)
                msgMsgVideoView.start();
            }

       //   msgMsgImageView.visibility=View.VISIBLE
         //  msgMsgImageView.setImageBitmap(msgItem.message_image as Bitmap)
            MainPage.MessageImagecClicked=0
        }*/
    }
}