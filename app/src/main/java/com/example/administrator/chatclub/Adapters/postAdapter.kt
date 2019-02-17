package com.example.administrator.chatclub.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.chatclub.*
import com.example.administrator.chatclub.Activities.Profile
import com.example.administrator.chatclub.Models.post
import com.example.administrator.chatclub.ViewHolders.PostImageViewHolder
import com.example.administrator.chatclub.ViewHolders.PostViewHolder


class PostListAdapter (val data:ArrayList<post>,
                       val onItemClick:(Int)->Unit, val onButtonClick:(Int)->Unit): RecyclerView.Adapter<PostViewHolder>() {


    override fun getItemCount(): Int =data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        if(Profile.CameraStatus ==0) {
            return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false))
        }
        else
        {
            return PostImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_inage_view, parent, false))
        }
    }
    override fun onBindViewHolder(p0: PostViewHolder, p1: Int) {

        //p0.bindItem(data[p1])

        p0.sharebutton.setOnClickListener{

           onButtonClick(p1)
        }

        p0.itemView.setOnClickListener {
            onItemClick(p1)
        }

    }
}