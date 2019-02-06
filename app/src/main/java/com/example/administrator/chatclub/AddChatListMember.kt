package com.example.administrator.chatclub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_chat_list_member.*
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlin.math.log

class AddChatListMember : AppCompatActivity(), ChildEventListener {

    lateinit var auth: FirebaseAuth
    lateinit var chatUserDB: DatabaseReference
    var CurrentUser: Users? = null
    lateinit var usersList: ArrayList<Users>
    lateinit var friendlist:ArrayList<String>
     lateinit var UserAdapter:AddChatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_chat_list_member)

        auth = FirebaseAuth.getInstance()
        usersList = arrayListOf()
        friendlist= arrayListOf()

        UserAdapter= AddChatListAdapter(usersList, ::onItemClick)

        MyAddChatList.adapter = UserAdapter
        MyAddChatList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)



        if (auth.currentUser == null) {
            exitChat()
            return
        }
        else
        {
            FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(auth.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            exitChat()
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            CurrentUser = snapshot.getValue(Users::class.java)
                            if (CurrentUser == null) {
                                exitChat()
                            }
                            else
                            {
                                friendlist.addAll(CurrentUser!!.FriendListsUid)
                            }


                        }
                    })
        }

        chatUserDB = FirebaseDatabase.getInstance().getReference("Chat_Users")
        chatUserDB.addChildEventListener(this)
    }
        fun onItemClick(position: Int)
        {

           friendlist.add(usersList[position].uid!!)
            FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(auth.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            exitChat()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.ref.child("friendListsUid").setValue(friendlist)
                        }
                    })


            startActivity(Intent(this, ChatListPage::class.java))

        }

        override fun onCancelled(p0: DatabaseError) {
        }
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }
        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val fbuser = p0.getValue(Users::class.java)
            val contains:Boolean=fbuser?.uid in friendlist
            if (fbuser != null && fbuser.uid!=CurrentUser?.uid && !contains) {
                UserAdapter.add(fbuser)
               // usersList.add(fbuser)
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }
        private fun exitChat(){
            auth.signOut()
            startActivity(Intent(this,MainPage::class.java))
            finish()
        }
}

