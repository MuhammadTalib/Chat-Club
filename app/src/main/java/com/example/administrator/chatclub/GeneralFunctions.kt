package com.example.administrator.chatclub

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.administrator.chatclub.Activities.MainPage
import com.example.administrator.chatclub.Models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
var auth: FirebaseAuth = FirebaseAuth.getInstance()
var chatUid:String?=null
var CurrentUser: Users? = null
var ProfileUser: Users?=null

fun setValueInFireBase(con:Context,path:String,value:Any)
{
    FirebaseDatabase.getInstance().getReference(path)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    con.exitChat()
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.ref.setValue(value)
                }
            })
}

fun Context.exitChat(){
    auth.signOut()
    startActivity(Intent(this, MainPage::class.java))
}