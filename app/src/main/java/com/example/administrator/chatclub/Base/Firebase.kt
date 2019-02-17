package com.example.administrator.chatclub.Base

import android.app.Service
import android.content.Intent
import android.support.annotation.NonNull
import com.example.administrator.chatclub.Activities.MainPage
import com.example.administrator.chatclub.Models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper
import java.util.*

open class Firebase:Variable()
{
    public var Authentication: FirebaseAuth = FirebaseAuth.getInstance()
    fun setValueInFireBase(path:String,value:Any)
    {
        FirebaseDatabase.getInstance().getReference(path)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        exitChat()
                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(value)
                    }
                })
    }

    fun exitChat(){
        Authentication.signOut()
        startActivity(Intent(this, MainPage::class.java))
        finish()
    }
}
