package com.example.administrator.chatclub

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

open class PostViewHolder(MyItem: View): RecyclerView.ViewHolder(MyItem)
{
  val name:TextView=MyItem.findViewById(R.id.postername)
  val myImage: ImageView =MyItem.findViewById(R.id.posterimage)
  val postcontent: TextView =MyItem.findViewById(R.id.postContent)
  val sharebutton:Button=MyItem.findViewById(R.id.shareButton)

  open fun bindItem(msgItem: com.example.administrator.chatclub.post){

    if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
      myImage.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Int)
    else if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Bitmap)
      myImage.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Bitmap)

    name.text=msgItem.userAccount.Username
    postcontent.text=msgItem.postContent


  }
}