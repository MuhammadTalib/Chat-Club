package com.example.administrator.chatclub.Adapters

import android.content.Context
import android.net.Uri
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.administrator.chatclub.*
import com.example.administrator.chatclub.Models.Message
import com.example.administrator.chatclub.ViewHolders.MessageViewHolder
import com.example.administrator.chatclub.ViewHolders.MsgViewHolderIn
import com.example.administrator.chatclub.ViewHolders.MsgViewHolderOut

class MessageListAdapter(val tHIS: Context, val msgs:ArrayList<Message>, val current_user_id:String) : RecyclerView.Adapter<MessageViewHolder>() {
    val MSG_IN = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if(viewType == MSG_IN){
            return MsgViewHolderIn(LayoutInflater.from(parent.context).inflate(R.layout.msg_item_in, parent, false))
        }else {
            return MsgViewHolderOut(LayoutInflater.from(parent.context).inflate(R.layout.msg_item_out, parent, false))
        }
    }

    override fun getItemCount(): Int = msgs.size

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int)
    {
        viewHolder.bindItem(msgs[position])
        if(msgs[position].image!=null)
        {
            Log.e("hahaha","not null")
            viewHolder.msgMsgImageView.visibility= View.VISIBLE

            Glide.with(tHIS).applyDefaultRequestOptions(RequestOptions().apply {
                placeholder(CircularProgressDrawable(tHIS).apply {
                    strokeWidth = 2f
                    centerRadius = 80f
                    start()
                })
            }).load(msgs[position].image).into(viewHolder.msgMsgImageView)

           // Glide.with(tHIS).load(msgs[position].image).into(viewHolder.msgMsgImageView)
        }
        if(msgs[position].image!=null)
        {
            Log.e("hahaha","not null")
            viewHolder.msgMsgImageView.visibility= View.VISIBLE

            Glide.with(tHIS).applyDefaultRequestOptions(RequestOptions().apply {
                placeholder(CircularProgressDrawable(tHIS).apply {
                    strokeWidth = 2f
                    centerRadius = 80f
                    start()
                })
            }).load(msgs[position].image).into(viewHolder.msgMsgImageView)

            // Glide.with(tHIS).load(msgs[position].image).into(viewHolder.msgMsgImageView)
        }
        else if(msgs[position].video!=null)
        {
            Log.e("hahaha","not null")
            viewHolder.msgMsgVideoView.visibility= View.VISIBLE

            //Glide.with(tHIS).load(msgs[position].video).into(viewHolder.msgMsgVideoView)

            viewHolder.msgMsgVideoView.setVideoURI(Uri.parse(msgs[position].video))
            viewHolder.msgMsgVideoView.start()


           // viewHolder.msgMsgVideoView.setVideoURI(msgs[position].video as Uri)

            // Glide.with(tHIS).load(msgs[position].image).into(viewHolder.msgMsgImageView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(current_user_id==msgs[position].userId)
        {
            return 1
        }
        else
        {   return 0
        }
    }
    fun add(msg: Message) {
        msgs.add(msg)
        notifyItemInserted(msgs.size-1)
    }
    fun remove(msg: Message) {
        msgs.remove(msg)
        notifyItemInserted(msgs.size-1)
    }
    fun change(msg: Message) {
        if(msg.image!=null)
            msgs[msgs.size-1].image=msg.image
        else if(msg.video!=null)
            msgs[msgs.size-1].video=msg.video
        notifyItemChanged(msgs.size-1)

    }
}

