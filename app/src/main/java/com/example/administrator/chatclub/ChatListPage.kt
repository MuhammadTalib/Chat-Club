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
import kotlinx.android.synthetic.main.activity_chat_list_page.*

class ChatListPage : AppCompatActivity() {

    lateinit var Authentication: FirebaseAuth
    lateinit var friendlist:ArrayList<Users>
    lateinit var chatuserAdapter:ChatListAdapter

    companion object {
        var chatUid:String?=null
        var CurrentUser:Users? = null
        var chatUser:Users?=null
    }

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
                            exitChat()
                        }
                        override fun onDataChange(snapshot: DataSnapshot)
                        {
                            CurrentUser = snapshot.getValue(Users::class.java)
                            if (CurrentUser == null) {
                                exitChat()
                            }
                            else
                            {
                                if(CurrentUser?.FriendListsUid?.size !=0) {
                                     for (i in CurrentUser!!.FriendListsUid) {
                                         var TempUser: Users? = null
                                         FirebaseDatabase.getInstance().getReference("Chat_Users")
                                                 .child(i.frienduid!!)
                                                 .addListenerForSingleValueEvent(object : ValueEventListener {
                                                     override fun onCancelled(p0: DatabaseError) {
                                                         //exitChat()
                                                     }

                                                     override fun onDataChange(snapshot: DataSnapshot) {
                                                         TempUser = snapshot.getValue(Users::class.java)
                                                         if(TempUser!=null)
                                                         {
                                                             chatuserAdapter.add(TempUser!!)
                                                         }
                                                     }

                                                 })
                                     }
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
    fun openMessageList(index:Int){
        chatUser=friendlist[index]
        chatUid= CurrentUser!!.FriendListsUid[index].chatUid
        var Intent=Intent(this,MessageList::class.java)
        startActivity(Intent)

    }
    fun openProfile(index:Int){

        startActivity(Intent(this,Profile::class.java))

    }
    private fun exitChat(){
        Authentication.signOut()
        startActivity(Intent(this,MainPage::class.java))
        finish()
    }
}
