package com.example.administrator.chatclub

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class PostImageViewHolder(MyItem: View): PostViewHolder(MyItem)
{
  val PostImage: ImageView = itemView.findViewById(R.id.postimage)

  override fun bindItem(msgItem: com.example.administrator.chatclub.post){
    super.bindItem(msgItem)
    PostImage.setImageBitmap(Profile.MyImageBitmap as Bitmap)
  }
}