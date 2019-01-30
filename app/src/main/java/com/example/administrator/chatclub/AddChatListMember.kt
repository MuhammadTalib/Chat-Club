package com.example.administrator.chatclub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_add_chat_list_member.*
import kotlinx.android.synthetic.main.activity_chat_list.*

class AddChatListMember : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_chat_list_member)

        MyAddChatList.layoutManager = LinearLayoutManager( this, LinearLayout.VERTICAL,false)
        MyAddChatList.adapter = AddChatListAdapter(MainPage.AccountData,::onItemClick)

    }
    fun onItemClick(position:String){
        t(position)
    }
}
