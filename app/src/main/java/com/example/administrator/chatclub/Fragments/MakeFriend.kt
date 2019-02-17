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
import android.widget.LinearLayout
import com.example.administrator.chatclub.Activities.FragmentsHolder
import com.example.administrator.chatclub.Adapters.AddChatListAdapter
import com.example.administrator.chatclub.Adapters.Request_Recieved_List_Adapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Base.FragmentBase
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.Models.frienddata
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.auth
import com.example.administrator.chatclub.setValueInFireBase
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_chat_list_member.*

class MakeFriend : FragmentBase(), ChildEventListener {


    lateinit var chatUserDB: DatabaseReference
    var CurrentUser: Users? = null
    var tempuser: Users?=null
    var TempUser: Users? = null
    lateinit var usersList: ArrayList<Users> //all user list
    lateinit var friendlist:ArrayList<frienddata>  //my friend list
    lateinit var recieverequest:ArrayList<Users> //my recieve request list
    lateinit var sentrequests:ArrayList<frienddata>     // my sent request list
    lateinit var friendrequestsendlist:ArrayList<frienddata> //friend's request list
    lateinit var UserAdapter: AddChatListAdapter
    lateinit var friend_request_Adapter: Request_Recieved_List_Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view= inflater.inflate(R.layout.fragment_make_friend, container, false)


        usersList = arrayListOf()
        friendlist= arrayListOf()
        friendrequestsendlist= arrayListOf()
        sentrequests= arrayListOf()
        recieverequest= arrayListOf()

        UserAdapter= AddChatListAdapter(usersList, ::onItemClick)
        friend_request_Adapter= Request_Recieved_List_Adapter(recieverequest, ::AddFriend)
        var MyAdddChatList:RecyclerView=view!!.findViewById(R.id.MyAddChatList)
        var FriendRlIST:RecyclerView=view!!.findViewById(R.id.friend_request_list)

        MyAdddChatList.adapter = UserAdapter
        MyAdddChatList.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        FriendRlIST.adapter=friend_request_Adapter
        FriendRlIST.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)



        if (auth.currentUser == null) {
            exitChat()
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
                                sentrequests.addAll(CurrentUser!!.sent_requests)

                                if(CurrentUser?.friend_requests?.size !=0) {
                                    nonewrequest.visibility=View.GONE
                                    pi.visibility= View.VISIBLE
                                    pii.visibility= View.VISIBLE
                                    piii.visibility= View.VISIBLE
                                    friend_request_list.visibility= View.VISIBLE
                                    for (i in CurrentUser!!.friend_requests) {
                                        var TempUser: Users?
                                        FirebaseDatabase.getInstance().getReference("Chat_Users")
                                                .child(i.frienduid!!)
                                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                                    override fun onCancelled(p0: DatabaseError) {
                                                                //exitChat()
                                                    }

                                                    override fun onDataChange(snapshot: DataSnapshot) {
                                                        TempUser = snapshot.getValue(Users::class.java)

                                                        if (TempUser != null) {
                                                            friend_request_Adapter.add(TempUser!!)
                                                        }
                                                    }

                                                })
                                    }
                                }

                            }


                        }
                    })
        }

        chatUserDB = FirebaseDatabase.getInstance().getReference("Chat_Users")
        chatUserDB.addChildEventListener(this)

        return view

    }
            fun onItemClick(position: Int)
            {
                toast(context!!,"Friend Request Send!")
                var newfriend: frienddata = frienddata().apply {
                    this.chatUid= auth.currentUser?.uid+usersList[position].uid
                    this.frienduid=usersList[position].uid
                }
                var newfriend2: frienddata = frienddata().apply {
                    this.chatUid=auth.currentUser?.uid+usersList[position].uid
                    this.frienduid=auth.currentUser?.uid
                }

                sentrequests.add(newfriend)

                setValueInFireBase(context!!,"Chat_Users/${auth.currentUser?.uid ?: ""}/sent_requests",sentrequests)
                FirebaseDatabase.getInstance().getReference("Chat_Users")
                        .child(usersList[position].uid ?: "")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                exitChat()
                            }
                            override fun onDataChange(snapshot: DataSnapshot) {
                                TempUser = snapshot.getValue(Users::class.java)
                                friendrequestsendlist.addAll(TempUser!!.friend_requests)
                                friendrequestsendlist.add(newfriend2)
                                snapshot.ref.child("friend_requests").setValue(friendrequestsendlist)
                            }
                        })
                startActivity(Intent(context, FragmentsHolder::class.java))

            }
            fun AddFriend(position: Int)
            {
                var friend_sentrequest: frienddata = frienddata().apply {
                    this.frienduid=CurrentUser?.uid
                    this.chatUid=CurrentUser!!.friend_requests[position].chatUid
                }

                FirebaseDatabase.getInstance().getReference("Chat_Users")
                        .child(CurrentUser!!.friend_requests[position].frienduid!!)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }
                            override fun onDataChange(snapshot: DataSnapshot) {

                                tempuser=snapshot.getValue(Users::class.java)
                                tempuser?.add_friendList(friend_sentrequest)
                                tempuser?.delete_sentRequest(friend_sentrequest)
                            }
                        })

                CurrentUser?.add_friendList(CurrentUser!!.friend_requests[position])
                CurrentUser?.delete_friendRequest(CurrentUser!!.friend_requests[position])
                startActivity(Intent(context, FragmentsHolder::class.java))

            }

            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val fbuser = p0.getValue(Users::class.java)
                val alreadyfriend:Boolean=fbuser?.uid in friendlist.map({item->item.frienduid})
                val requestsend:Boolean=fbuser?.uid in CurrentUser!!.sent_requests.map({item->item.frienduid})
                if (fbuser != null && fbuser.uid!=CurrentUser?.uid && !alreadyfriend && !requestsend)
                {
                    nonewrequest.visibility=View.GONE
                    pe.visibility=View.VISIBLE
                    pee.visibility=View.VISIBLE
                    MyAddChatList.visibility=View.VISIBLE
                    UserAdapter.add(fbuser)
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }


}
