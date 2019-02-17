package com.example.administrator.chatclub.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.administrator.chatclub.*
import com.example.administrator.chatclub.Activities.AddChatListMember
import com.example.administrator.chatclub.Activities.GroupMaker
import com.example.administrator.chatclub.Activities.MessageList
import com.example.administrator.chatclub.Activities.Profile
import com.example.administrator.chatclub.Adapters.ChatListAdapter
import com.example.administrator.chatclub.Adapters.GroupListAdapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Base.Firebase
import com.example.administrator.chatclub.Base.FragmentBase
import com.example.administrator.chatclub.Base.Variable
import com.example.administrator.chatclub.Models.GroupOfUsers
import com.example.administrator.chatclub.Models.Users

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_list_page.*
import kotlinx.android.synthetic.main.fragment_chat_list.*


class ChatList : FragmentBase() {

    lateinit var friendlist:ArrayList<Users>
    lateinit var grouplist:ArrayList<GroupOfUsers>
    lateinit var chatuserAdapter: ChatListAdapter
    lateinit var GroupuserAdapter: GroupListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        grouplist= arrayListOf()
        friendlist= arrayListOf()

        auth = FirebaseAuth.getInstance()

        var view= inflater.inflate(R.layout.fragment_chat_list, container, false)

        var grouplist_view:RecyclerView=view!!.findViewById(R.id.mygrouplist)
        var chatlist_view:RecyclerView=view!!.findViewById(R.id.mychatlist)
        var add_user:ImageView=view.findViewById(R.id.add)
        var log_out:ImageView=view.findViewById(R.id.logout)
        var makeGroup:ImageView=view.findViewById(R.id.groupactivity)

        chatuserAdapter= ChatListAdapter(friendlist, ::openMessageList, ::openProfile)
        GroupuserAdapter= GroupListAdapter(grouplist, ::openMessageList)

        grouplist_view.adapter=GroupuserAdapter
        grouplist_view.layoutManager= LinearLayoutManager( context, LinearLayout.VERTICAL,false)

        chatlist_view.adapter =chatuserAdapter
        chatlist_view.layoutManager = LinearLayoutManager( context, LinearLayout.VERTICAL,false)

        if (auth.currentUser == null) {
            exitChat()
        }
        else{
            LoadFriends()
        }
        add_user.setOnClickListener {
            startActivity(Intent(context, AddChatListMember::class.java))
        }
        log_out.setOnClickListener {
            exitChat()
        }
        makeGroup.setOnClickListener {
            startActivity(Intent(context, GroupMaker::class.java))
        }
        return view
    }

    fun openMessageList(index:Int) {
        if(index >= CurrentUser?.GroupListUid?.size!!)
            chatUid = CurrentUser!!.FriendListsUid[index- CurrentUser?.GroupListUid?.size!!].chatUid
        else
            chatUid =friendlist[index].uid
        startActivity( Intent(context, MessageList::class.java))
    }
    fun openProfile(index:Int){
        if(index >= CurrentUser?.GroupListUid?.size!!) {
            ProfileUser =friendlist[index+ CurrentUser?.GroupListUid?.size!!]
            startActivity(Intent(context, Profile::class.java))
        }
    }
    fun LoadFriends()
    {
        FirebaseDatabase.getInstance().getReference("Chat_Users")
                .child(auth.currentUser?.uid ?: "")
                .addListenerForSingleValueEvent(object : ValueEventListener
                {
                    override fun onCancelled(p0: DatabaseError) {
                        exitChat()
                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        CurrentUser = snapshot.getValue(Users::class.java)
                        if (CurrentUser == null) {
                            exitChat()
                        }
                        else {
                            chatuserAdapter.addAll(CurrentUser?.GroupListUid?.map { u-> Users().apply {
                                this.Username=u.Name
                                this.Email="is_group"
                                this.uid=u.uid
                            } } as ArrayList<Users>)

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



}


