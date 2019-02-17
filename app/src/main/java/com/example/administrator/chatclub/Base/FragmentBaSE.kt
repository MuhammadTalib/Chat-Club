package com.example.administrator.chatclub.Base

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.administrator.chatclub.Activities.MainPage
import com.example.administrator.chatclub.auth

open class FragmentBase:Fragment() {

    fun exitChat(){
        auth.signOut()
        startActivity(Intent(context, MainPage::class.java))
    }
    fun t(con:Context,m:String) {
        Toast.makeText(con, m, Toast.LENGTH_SHORT).show()
    }
    fun toast(con:Context,msg:String?){
        Toast.makeText(con,msg ?: "", Toast.LENGTH_LONG).show()
    }
    fun View.show(){
        visibility = View.VISIBLE
    }
    fun View.hide(){
        visibility = View.GONE
    }

}