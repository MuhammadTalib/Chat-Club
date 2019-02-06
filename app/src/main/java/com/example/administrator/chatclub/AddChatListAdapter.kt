package com.example.administrator.chatclub

import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

class AddChatListAdapter ( val data:ArrayList<Users>,
                        val onItemClick:(Int)->Unit): RecyclerView.Adapter<AddChatListViewHolder>() {


    var temp=0
    override fun getItemCount(): Int =data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddChatListViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.add_chat_list_view,parent,false)
        return AddChatListViewHolder(itemView)
    }
    override fun onBindViewHolder(p0: AddChatListViewHolder, p1: Int)
    {
        //if(data[p1]!=MainPage.AccountData[MainPage.MyAccountIndex])
       // {
            /*if(data[p1].userImage is Int)
                p0.myImage.setImageResource(data[p1].userImage as Int)
            else if(data[p1].userImage is Bitmap)
                p0.myImage.setImageBitmap(data[p1].userImage as Bitmap)
*/
            p0.myText.text=data[p1].Username
           //p0.myStatus.text=data[p1].lastmessage
            p0.myAddButton.setOnClickListener {
                Log.e("hahaha","${p1}")
                 onItemClick(p1)

            }

          //  p0.myAddButton.setOnClickListener {

           /* for(i in MainPage.AccountData[MainPage.MyAccountIndex].FriendList)
            {
                if(i.Username==data[p1].Username)
                {
                    temp++;
                }
            }
            if(temp==0)
            {
              //  MainPage.AccountData[MainPage.MyAccountIndex].FriendList.add(data[p1])
            }
            else
            {
                onItemClick("Item Already Exist..")
            }*/
       // }

    }
    fun add(item:Users){
        data.add(item)
        notifyItemInserted(data.size-1)
    }



}
