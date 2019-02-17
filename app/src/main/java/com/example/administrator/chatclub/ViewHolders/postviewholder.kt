package com.example.administrator.chatclub.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.administrator.chatclub.R

open class PostViewHolder(MyItem: View): RecyclerView.ViewHolder(MyItem)
{
  val name:TextView=MyItem.findViewById(R.id.postername)
  val myImage: ImageView =MyItem.findViewById(R.id.posterimage)
  val postcontent: TextView =MyItem.findViewById(R.id.postContent)
  val sharebutton:Button=MyItem.findViewById(R.id.shareButton)

  open fun bindItem(){

   /* if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
      myImage.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Int)
    else if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Bitmap)
      myImage.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Bitmap)
*/
   // name.text=msgItem.userAccount.Username
  //  postcontent.text=msgItem.postContent


  }
}