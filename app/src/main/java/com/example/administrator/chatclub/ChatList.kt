package com.example.administrator.chatclub

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_chat_list.*
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_profile.*


class ChatList : AppCompatActivity() {
    companion object {

        var friend=0
        var new=UserAccount("","","")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        getSupportActionBar()?.hide()


        if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
            userImage.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Int)
        else if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Bitmap)
            userImage.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Bitmap)

        MyChatList.layoutManager = LinearLayoutManager( this, LinearLayout.VERTICAL,false)
        MyChatList.adapter = ChatListAdapter(MainPage.AccountData[MainPage.MyAccountIndex].FriendList,::openMessageList)

        userImage.setOnClickListener{
           var intent=Intent(this,Profile::class.java)
            startActivity(intent)
        }

        add.setOnClickListener {
            val intent= Intent(this,AddChatListMember::class.java)
            startActivityForResult(intent,10000)
        }


    }
    override fun onResume()
    {
        super.onResume()
        MyChatList.adapter?.notifyItemChanged(MainPage.AccountData[MainPage.MyAccountIndex].FriendList.size)
    }

    fun openMessageList(AnotherUserIndex:Int){
        new=MainPage.AccountData[AnotherUserIndex]
        friend=AnotherUserIndex
        Log.e("hold","$friend")
        val intent= Intent(this,MessageList::class.java)
        startActivityForResult(intent,10000)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode== Activity.RESULT_OK)
        {
            val intent= Intent(this,ChatList::class.java)
            startActivity(intent)
        }


    }

}
