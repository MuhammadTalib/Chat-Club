package com.example.administrator.chatclub.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.administrator.chatclub.Adapters.ChatListAdapter
import com.example.administrator.chatclub.Adapters.GroupListAdapter
import com.example.administrator.chatclub.CurrGroup
import com.example.administrator.chatclub.CurrUser
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.chatUid
import kotlinx.android.synthetic.main.activity_chat_list_page.*
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {

    lateinit var GroupMembers: ChatListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        group_name.text= CurrGroup?.Name
        GroupMembers= ChatListAdapter(this,CurrGroup?.UsersList!!, ::openMessageList, ::openProfile)

        group_members.adapter=GroupMembers
        group_members.layoutManager= LinearLayoutManager( this, LinearLayout.VERTICAL,false)
    }
    fun openMessageList(index:Int) {
    }
    fun openProfile(index:Int){
    }
}
