package com.example.administrator.chatclub.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.example.administrator.chatclub.R

class GroupViewHolder (MyItem: View): RecyclerView.ViewHolder(MyItem)
{
   // val myImage: ImageView =MyItem.findViewById(R.id.image)
    val UserName: TextView =MyItem.findViewById(R.id.userName)
    val CheckBox: CheckBox =MyItem.findViewById(R.id.select)
    //val myAddButton: ImageView =MyItem.findViewById(R.id.add)
}