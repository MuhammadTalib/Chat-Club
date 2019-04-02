package com.example.administrator.chatclub.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.administrator.chatclub.*
import com.example.administrator.chatclub.Activities.GroupActivity
import com.example.administrator.chatclub.Activities.MessageList
import com.example.administrator.chatclub.Activities.Profile
import com.example.administrator.chatclub.Adapters.ChatListAdapter
import com.example.administrator.chatclub.Base.FragmentBase
import com.example.administrator.chatclub.Models.GroupOfUsers
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.linkpath
import com.example.administrator.chatclub.Models.usertempdata
import com.example.administrator.chatclub.R
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class ChatList : FragmentBase(){

    lateinit var DBs: DatabaseReference
    var CurrentUser: Users? = null
    lateinit var friendlist:ArrayList<usertempdata>
    lateinit var grouplist:ArrayList<GroupOfUsers>
    lateinit var chatuserAdapter: ChatListAdapter
    lateinit var GroupDB:DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        if (!linkpath!!.isEmpty()) {
            val segments = linkpath?.split("/")
            if(segments!![1]=="profile"){
                t(context!!,segments[2])
                FirebaseDatabase.getInstance().getReference("Chat_Users").orderByChild("email").equalTo(segments[2]+"@chatclub.com").addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val uid=snapshot.children.last().key
                                FirebaseDatabase.getInstance().getReference("Chat_Users")
                                        .child(uid ?: "")
                                        .addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError) {
                                                exitChat()
                                            }

                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                ProfileUser = snapshot.getValue(usertempdata::class.java)
                                                startActivity(Intent(context!!,Profile::class.java))
                                            }
                                        })
                            }

                            override fun onCancelled(databaseError: DatabaseError) {

                            }
                        }
                )
            }
        }

        friendlist= arrayListOf()
        grouplist= arrayListOf()

        var view= inflater.inflate(R.layout.fragment_chat_list, container, false)
        var chatlist_view:RecyclerView=view.findViewById(R.id.mychatlist)

        chatuserAdapter= ChatListAdapter(context!!,friendlist, ::openMessageList, ::openProfile)
        chatlist_view.adapter =chatuserAdapter
        chatlist_view.layoutManager = LinearLayoutManager( context, LinearLayout.VERTICAL,false)

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

                            } else {
                                DBs = FirebaseDatabase.getInstance().getReference("Chat_Users/${CurrentUser?.uid}/FriendListsUid")
                                DBs.addChildEventListener(object:ChildEventListener{
                                    override fun onCancelled(p0: DatabaseError) {
                                        Log.e("hahaha","cancelled")
                                    }
                                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                                        Log.e("hahaha","moved")
                                    }
                                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                                        Log.e("hahahaf","sonthing changed")
                                        val fbuser = p0.getValue(usertempdata::class.java)
                                        for(i in friendlist.indices)
                                        {
                                            if(friendlist[i].uid==fbuser?.uid)
                                            {
                                                friendlist.removeAt(i)
                                                friendlist.add(i, fbuser!!)
                                                chatuserAdapter.notifyItemChanged(i)
                                            }
                                        }
                                    }
                                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                        val fbuser=p0.getValue(usertempdata::class.java)
                                        friendlist.add(fbuser!!)
                                        chatuserAdapter.notifyItemInserted(friendlist.size-1)
                                    }
                                    override fun onChildRemoved(p0: DataSnapshot) {
                                        Log.e("hahaha","removed")
                                    }
                                })
                                GroupDB = FirebaseDatabase.getInstance().getReference("Chat_Users/${CurrentUser?.uid}/GroupListUid")
                                GroupDB.addChildEventListener(object :ChildEventListener{
                                    override fun onCancelled(p0: DatabaseError) {
                                        Log.e("hahaha","cancelled")
                                    }
                                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                                        Log.e("hahaha","moved")
                                    }
                                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                                        Log.e("hahaha","onChildChanged")
                                    }
                                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                        val fbuser=p0.getValue(GroupOfUsers::class.java)
                                        grouplist.add(0,fbuser!!)
                                        friendlist.add(0,usertempdata().apply {
                                            this.Username=fbuser.Name
                                            this.uid=fbuser.uid
                                            this.chatid=fbuser.uid
                                            this.Email="is group"
                                        })
                                    }

                                    override fun onChildRemoved(p0: DataSnapshot) {
                                        Log.e("hahaha","onChildRemoved")
                                    }

                                })

                            }

                        }

                    })
        }
        return view
    }

    fun openMessageList(index:Int) {
        chatUid =friendlist[index].chatid
        startActivity( Intent(context, MessageList::class.java))
    }
    fun openProfile(index:Int){
        if(friendlist[index].Email=="is group") {
            CurrGroup=grouplist[index]
            startActivity(Intent(context, GroupActivity::class.java))
        }else {
            ProfileUser=friendlist[index]
            startActivity(Intent(context, Profile::class.java))
        }
    }

}

