package com.example.administrator.chatclub.Base

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.administrator.chatclub.Activities.MainPage
import com.example.administrator.chatclub.auth
import com.example.administrator.chatclub.exitChat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

}