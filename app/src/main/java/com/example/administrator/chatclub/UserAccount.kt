package com.example.administrator.chatclub

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView

class UserAccount(var Email:String,var Username:String,var password:String)
{
    var CoverPhoto:Any=R.drawable.ic_person
    var userImage:Any=R.drawable.ic_person
    var lastmessage:String="Say Hi to you new friend ${this.Username}"
    var personalposts=ArrayList<post>()
    var FriendList=ArrayList<UserAccount>()
    var MessageArray= arrayListOf<Message>(Message("Say Hi to you new friend ${this.Username}",0))

}