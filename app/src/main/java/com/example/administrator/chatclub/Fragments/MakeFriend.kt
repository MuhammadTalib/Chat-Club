package com.example.administrator.chatclub.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.administrator.chatclub.Activities.FragmentsHolder
import com.example.administrator.chatclub.Adapters.AddChatListAdapter
import com.example.administrator.chatclub.Adapters.Request_Recieved_List_Adapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Base.FragmentBase
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.Models.frienddata
import com.example.administrator.chatclub.Models.usertempdata
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.auth
import com.example.administrator.chatclub.setValueInFireBase
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_chat_list_member.*
import android.os.Handler;





class MakeFriend : FragmentBase() {


    lateinit var chatUserDB: DatabaseReference
    lateinit var friendrequestDB: DatabaseReference
    var CurrentUser: Users? = null

    lateinit var usersList: ArrayList<usertempdata> //all user list
    lateinit var recieverequest: ArrayList<usertempdata> //my recieve request list
    lateinit var sentrequests: ArrayList<usertempdata>     // my sent request list
    lateinit var friendrequestsendlist: ArrayList<frienddata> //friend's request list

    lateinit var UserAdapter: AddChatListAdapter
    lateinit var friend_request_Adapter: Request_Recieved_List_Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_make_friend, container, false)


        usersList = arrayListOf()
        friendrequestsendlist = arrayListOf()
        sentrequests = arrayListOf()
        recieverequest = arrayListOf()

        UserAdapter = AddChatListAdapter(context!!,usersList, ::onItemClick)
        friend_request_Adapter = Request_Recieved_List_Adapter(context!!,recieverequest, ::AddFriend)

        var MyAdddChatList: RecyclerView = view.findViewById(R.id.MyAddChatList)
        var FriendList: RecyclerView = view.findViewById(R.id.friend_request_list)

        MyAdddChatList.adapter = UserAdapter
        MyAdddChatList.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        FriendList.adapter = friend_request_Adapter
        FriendList.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        if (auth.currentUser == null) {
            exitChat()
        } else {
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
                        }
                    })
        }

        chatUserDB = FirebaseDatabase.getInstance().getReference("Chat_Users")
        chatUserDB.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("hahaha","cancelled") }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                Log.e("hahaha","moved") }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                Log.e("hahaha","sonnnthing is changed")
                val fbuser = p0.getValue(usertempdata::class.java)
                for(i in usersList.indices)
                {
                    Log.e("hahaha","ittrating")
                    if(usersList[i].uid==fbuser?.uid)
                    {
                        usersList.removeAt(i)
                        usersList.add(i, fbuser!!)
                        UserAdapter.notifyItemChanged(i)
                    }
                }
            }
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val fbuser = p0.getValue(usertempdata::class.java)
                FirebaseDatabase.getInstance().getReference("Chat_Users/${auth.currentUser?.uid}/sent_requests")
                        .child(fbuser?.uid?: "")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                Log.e("user","cancelled")
                                exitChat()
                            }
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val sent = snapshot.getValue(usertempdata::class.java)
                                if(sent!=null) {
                                    Log.e("hahaha","sent ${sent.Username}")
                                }else
                                    if (fbuser != null && fbuser.uid != CurrentUser?.uid ) {

                                        FirebaseDatabase.getInstance().getReference("Chat_Users/${auth.currentUser?.uid}/friend_requests")
                                                .child(fbuser?.uid?: "")
                                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                                    override fun onCancelled(p0: DatabaseError) {
                                                        Log.e("user","cancelled")
                                                        exitChat()
                                                    }
                                                    override fun onDataChange(snapshot: DataSnapshot) {
                                                        val rece:usertempdata? = snapshot.getValue(usertempdata::class.java)
                                                        if(rece!=null) {
                                                        }else {
                                                            FirebaseDatabase.getInstance().getReference("Chat_Users/${auth.currentUser?.uid}/FriendListsUid")
                                                                    .child(fbuser?.uid?: "")
                                                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                                                        override fun onCancelled(p0: DatabaseError) {
                                                                            Log.e("user","cancelled")
                                                                            exitChat()
                                                                        }
                                                                        override fun onDataChange(snapshot: DataSnapshot) {
                                                                            val friend:usertempdata? = snapshot.getValue(usertempdata::class.java)
                                                                            if(friend!=null) {
                                                                            }else {
                                                                                nonewrequest.visibility = View.GONE
                                                                                pe.visibility = View.VISIBLE
                                                                                pee.visibility = View.VISIBLE
                                                                                MyAdddChatList.visibility = View.VISIBLE
                                                                                UserAdapter.add(fbuser)
                                                                            }
                                                                        }
                                                                    })
                                                        }
                                                    }
                                                })
                                    }
                            }
                        })

            }
            override fun onChildRemoved(p0: DataSnapshot) {
                Log.e("hahaha","removedaaaa")
               }

        })

        friendrequestDB = FirebaseDatabase.getInstance().getReference("Chat_Users/${auth.currentUser?.uid}/friend_requests")
        friendrequestDB.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("hahaha","sonthing cancelled")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                Log.e("hahaha","sonthing moved")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                Log.e("hahahaf","sonthing changed")
                val fbuser = p0.getValue(usertempdata::class.java)
                for(i in recieverequest.indices)
                {
                    if(recieverequest[i].uid==fbuser?.uid)
                    {
                        recieverequest.removeAt(i)
                        recieverequest.add(i, fbuser!!)
                        friend_request_Adapter.notifyItemChanged(i)
                    }
                }
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                Log.e("hahaha","something ")
                var temp = p0.getValue(usertempdata::class.java)
                nonewrequest.visibility = View.GONE
                friend_request_list.visibility=View.VISIBLE
                pi.visibility=View.VISIBLE
                pii.visibility=View.VISIBLE
                piii.visibility=View.VISIBLE
                friend_request_Adapter.add(temp!!)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val fbuser = p0.getValue(usertempdata::class.java)
                for(i in recieverequest.indices)
                {
                    if(recieverequest[i].uid==fbuser?.uid)
                    {
                        //recieverequest.removeAt(i)
                       // friend_request_Adapter.notifyItemChanged(i)
                    }
                }
            }

        })

        return view

    }

    fun onItemClick(position: Int) {

        FirebaseDatabase.getInstance().getReference("Chat_Users/${auth.currentUser?.uid!!}/sent_requests").push().key!!
        FirebaseDatabase.getInstance()
                .getReference("Chat_Users")
                .child(CurrentUser?.uid!!)
                .child("sent_requests")
                .child(usersList[position].uid!!)
                .setValue(usertempdata().apply {
                    this.Username = usersList[position].Username
                    this.Email = usersList[position].Email
                    this.chatid = auth.currentUser?.uid + usersList[position].uid
                    this.uid = usersList[position].uid
                    this.profilepic =usersList[position].profilepic
                })
        FirebaseDatabase.getInstance()
                .getReference("Chat_Users")
                .child(usersList[position].uid ?: "")
                .child("friend_requests")
                .child(auth.currentUser?.uid!!)
                .setValue(usertempdata().apply {
                    this.Email=CurrentUser?.Email
                    this.Username=CurrentUser?.username
                    this.chatid = auth.currentUser?.uid + usersList[position].uid
                    this.uid = auth.currentUser?.uid
                    this.profilepic = CurrentUser?.profilepic
                }).addOnCompleteListener {
                    Handler().postDelayed({
                        usersList.removeAt(position)
                        UserAdapter.notifyItemRemoved(position)
                    }, 500)
                }
    }
    fun AddFriend(position: Int) {
        FirebaseDatabase.getInstance()
                .getReference("Chat_Users")
                .child(CurrentUser?.uid!!)
                .child("FriendListsUid")
                .child(recieverequest[position].uid!!)
                .setValue(recieverequest[position])
        FirebaseDatabase.getInstance()
                .getReference("Chat_Users")
                .child(recieverequest[position].uid!!)
                .child("FriendListsUid")
                .child(CurrentUser?.uid!!)
                .setValue(usertempdata().apply {
                     this.Username=CurrentUser?.username
                    this.Email=CurrentUser?.Email
                    this.profilepic=CurrentUser?.profilepic
                    this.chatid=recieverequest[position].chatid
                    this.uid=CurrentUser?.uid
                }).addOnCompleteListener {
                     Handler().postDelayed({
                         recieverequest.removeAt(position)
                         if(recieverequest.size<1)
                         {
                             pi.visibility=View.GONE
                             pii.visibility=View.GONE
                             piii.visibility=View.GONE
                         }
                         friend_request_Adapter.notifyItemRemoved(position)
                     }, 400)
                    FirebaseDatabase.getInstance()
                            .getReference("Chat_Users/${auth.currentUser?.uid!!}/friend_requests")
                            .child(recieverequest[position].uid!!)
                            .removeValue()
                    FirebaseDatabase.getInstance()
                            .getReference("Chat_Users/${recieverequest[position].uid!!}/sent_requests")
                            .child(CurrentUser?.uid!!)
                            .removeValue()
                }
    }
}
