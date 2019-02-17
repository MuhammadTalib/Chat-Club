package com.example.administrator.chatclub.Base

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.administrator.chatclub.Activities.MainPage
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity:Firebase()
{


    fun Context.t(m:String) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
    }
    fun Context.toast(msg:String?){
        Toast.makeText(this,msg ?: "", Toast.LENGTH_LONG).show()
    }
    fun View.show(){
        visibility = View.VISIBLE
    }
    fun View.hide(){
        visibility = View.GONE
    }

}
