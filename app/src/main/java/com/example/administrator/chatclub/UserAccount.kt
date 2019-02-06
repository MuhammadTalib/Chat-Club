package com.example.administrator.chatclub

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView

class UserAccount
{
    var Email:String?=null
    var Username:String?=null
    var password:String?=null
    var uid:String?=null
    var Country:String?=null
    var CoverPhoto:Any?=null
    var userImage:Any?=null
    var lastmessage:String="Say Hi to you new friend ${this.Username}"
    var personalposts=ArrayList<post>()
    var FriendList=ArrayList<UserAccount>()
    var FriendLists= arrayListOf<String>("Talib")
    var MessageArray= arrayListOf<Message>(Message("Say Hi to you new friend ${this.Username}",0))
}