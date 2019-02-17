package com.example.administrator.chatclub.ViewHolders

import android.view.View
import android.widget.ImageView
import com.example.administrator.chatclub.R

class PostImageViewHolder(MyItem: View): PostViewHolder(MyItem)
{
  val PostImage: ImageView = itemView.findViewById(R.id.postimage)
/*
  override fun bindItem(msgItem:post){
    super.bindItem(msgItem)
    PostImage.setImageBitmap(Profile.MyImageBitmap as Bitmap)
  }*/
}