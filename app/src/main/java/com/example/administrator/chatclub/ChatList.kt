package com.example.administrator.chatclub

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_list.*


class ChatList : AppCompatActivity() {

    lateinit var Authentication: FirebaseAuth
    var CurrentUser:Users? = null
    lateinit var friendlist:ArrayList<Users>
    lateinit var chatuserAdapter:ChatListAdapter
    companion object {
        var friend=0
        //var new=Users()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        getSupportActionBar()?.hide()

        friendlist= arrayListOf(Users())

        Authentication = FirebaseAuth.getInstance()

        /* if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
            userImage.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Int)
        else if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Bitmap)
            userImage.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Bitmap)
*/
        chatuserAdapter=ChatListAdapter(friendlist,::openMessageList,::openProfile)

        mychatlist.adapter =chatuserAdapter
        mychatlist.layoutManager = LinearLayoutManager( this, LinearLayout.VERTICAL,false)


        if (Authentication.currentUser == null) {
            exitChat()
            return
        }
        else
        {
            FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(Authentication.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener
                    {
                        override fun onCancelled(p0: DatabaseError)
                        {
                            Log.e("hahaha","OnCancelled")
                            exitChat()
                        }
                        override fun onDataChange(snapshot: DataSnapshot)
                        {
                            Log.e("hahaha","OnDataChanged")
                            CurrentUser = snapshot.getValue(Users::class.java)
                            if (CurrentUser == null) {
                                Log.e("hahaha","Current User Null")
                                exitChat()
                            }
                            else
                            {
                                Log.e("hahaha","Current User Not Null")
                               // Log.e("hahaha","${CurrentUser?.FriendListsUid?.size}")
                               /* if(CurrentUser?.FriendListsUid?.size !=0) {
                                    for (i in CurrentUser!!.FriendListsUid) {
                                        Log.e("hahaha","gggg")
                                        var TempUser: Users? = null
                                        FirebaseDatabase.getInstance().getReference("Chat_Users")
                                                .child(i)
                                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                                    override fun onCancelled(p0: DatabaseError) {
                                                        //exitChat()
                                                    }

                                                    override fun onDataChange(snapshot: DataSnapshot) {
                                                        TempUser = snapshot.getValue(Users::class.java)
                                                        if(TempUser!=null)
                                                        {
                                                            chatuserAdapter.add(TempUser!!)
                                                            Log.e("hahaha", "TEMP USER= ${TempUser?.Username}")
                                                        }
                                                    }

                                                })
                                    }
                                }
                                else
                                {
                                    Log.e("hahaha","uid is null")
                                }*/
                            }
                        }
                    })
        }
       // Log.e("hahaha","hehehe")

        userImage.setOnClickListener {
            var intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        add.setOnClickListener {
            val intent = Intent(this, AddChatListMember::class.java)
            startActivityForResult(intent, 10000)
        }
        logout.setOnClickListener {
            exitChat()
            finish()
        }

    }


    fun openMessageList(index:Int){

       startActivity(Intent(this,MessageList::class.java))

    }
    fun openProfile(index:Int){

        startActivity(Intent(this,Profile::class.java))

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode== Activity.RESULT_OK)
        {
           // val intent= Intent(this,ChatList::class.java)
            //startActivity(intent)
        }
    }
    private fun exitChat(){
        Authentication.signOut()
       startActivity(Intent(this,MainPage::class.java))
        finish()
    }

}




