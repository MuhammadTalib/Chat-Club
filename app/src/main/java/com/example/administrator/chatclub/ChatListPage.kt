package com.example.administrator.chatclub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListPage : AppCompatActivity() {

    lateinit var Authentication: FirebaseAuth
    var CurrentUser:Users? = null
    lateinit var friendlist:ArrayList<Users>
    lateinit var chatuserAdapter:ChatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list_page)
        getSupportActionBar()?.hide()
        friendlist= arrayListOf()

        Authentication = FirebaseAuth.getInstance()

        /* if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
            userImage.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Int)
        else if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Bitmap)
            userImage.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Bitmap)
*/

        chatuserAdapter=ChatListAdapter(friendlist)

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
                                 Log.e("hahaha","${CurrentUser?.FriendListsUid?.size}")
                                if(CurrentUser?.FriendListsUid?.size !=0) {
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
                                 }
                            }
                        }
                    })
        }

        userImage.setOnClickListener {
            var intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        add.setOnClickListener {
            startActivity(Intent(this, AddChatListMember::class.java))
        }
        logout.setOnClickListener {
            exitChat()
            finish()
        }

    }

    private fun exitChat(){
        Authentication.signOut()
        startActivity(Intent(this,MainPage::class.java))
        finish()
    }
}
