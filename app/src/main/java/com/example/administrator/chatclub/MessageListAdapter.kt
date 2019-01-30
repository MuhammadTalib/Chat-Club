package com.example.administrator.chatclub

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

class MessageListAdapter(val msgs:ArrayList<Message>) : RecyclerView.Adapter<MessageViewHolder>() {
    val MSG_IN = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
     if(viewType == MSG_IN){
            return MsgViewHolderIn(LayoutInflater.from(parent.context).inflate(R.layout.msg_item_in,parent,false))
        }else {
            return MsgViewHolderOut(LayoutInflater.from(parent.context).inflate(R.layout.msg_item_out,parent,false))
        }
    }

    override fun getItemCount(): Int = msgs.size

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int)
    {
        Log.e("holddd","$position")
        viewHolder.bindItem(msgs[position])

       // if(MainPage.AccountData[ChatList.friend].userImage is Uri)
           // viewHolder.userimage.setImageURI(MainPage.AccountData[ChatList.friend].userImage as Uri)
    }

    override fun getItemViewType(position: Int): Int {
        return msgs[position].msgSentBy
    }
}