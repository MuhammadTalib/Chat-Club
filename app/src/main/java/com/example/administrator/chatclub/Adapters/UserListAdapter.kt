package com.example.administrator.chatclub.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.chatclub.ViewHolders.GroupViewHolder
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.Models.Users

class UserListAdapter(val data:ArrayList<Users>,
                      val OnCheckboxChecked:(Int)->Unit,
                      val OnCheckboxUnChecked:(Int)->Unit): RecyclerView.Adapter<GroupViewHolder>() {


    override fun getItemCount(): Int =data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.group_item_view,parent,false)
        return GroupViewHolder(itemView)
    }
    override fun onBindViewHolder(p0: GroupViewHolder, p1: Int)
    {
        p0.UserName.text=data[p1].Username
        p0.CheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked)
                OnCheckboxChecked(p1)
            else
                OnCheckboxUnChecked(p1)

        }

        /*  //  p0.myAddButton.setOnClickListener {

          for(i in MainPage.AccountData[MainPage.MyAccountIndex].FriendList)
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
           }
       // }*/

    }
    fun add(item: Users){
        data.add(item)
        notifyItemInserted(data.size-1)
    }



}
