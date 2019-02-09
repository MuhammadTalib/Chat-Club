package com.example.administrator.chatclub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_chat_list_member.*
import kotlin.math.log

class AddChatListMember : AppCompatActivity(), ChildEventListener {

    lateinit var auth: FirebaseAuth
    lateinit var chatUserDB: DatabaseReference
    var CurrentUser: Users? = null
    var tempuser:Users?=null
    var TempUser: Users? = null
    lateinit var usersList: ArrayList<Users> //all user list
    lateinit var friendlist:ArrayList<frienddata>  //my friend list
    lateinit var recieverequest:ArrayList<Users> //my recieve request list
    lateinit var sentrequests:ArrayList<frienddata>     // my sent request list
    lateinit var friendrequestsendlist:ArrayList<frienddata> //friend's request list

    lateinit var UserAdapter:AddChatListAdapter
    lateinit var friend_request_Adapter:Request_Recieved_List_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_chat_list_member)

        auth = FirebaseAuth.getInstance()
        usersList = arrayListOf()
        friendlist= arrayListOf()
        friendrequestsendlist= arrayListOf()
        sentrequests= arrayListOf()
        recieverequest= arrayListOf()

        UserAdapter= AddChatListAdapter(usersList, ::onItemClick)
        friend_request_Adapter=Request_Recieved_List_Adapter(recieverequest, ::AddFriend)

        MyAddChatList.adapter = UserAdapter
        MyAddChatList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        friend_request_list.adapter=friend_request_Adapter
        friend_request_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)



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
                                sentrequests.addAll(CurrentUser!!.sent_requests)

                                if(CurrentUser?.friend_requests?.size !=0) {

                                    nonewrequest.visibility=View.GONE
                                    pi.visibility= View.VISIBLE
                                    pii.visibility= View.VISIBLE
                                    piii.visibility= View.VISIBLE
                                    friend_request_list.visibility= View.VISIBLE

                                    for (i in CurrentUser!!.friend_requests) {
                                        var TempUser: Users? = null
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
    }
        fun onItemClick(position: Int)
        {
            toast("Friend Request Send!")
            var newfriend:frienddata=frienddata().apply {
                this.chatUid=auth.currentUser?.uid+usersList[position].uid
                this.frienduid=usersList[position].uid
           }
            var newfriend2:frienddata=frienddata().apply {
                this.chatUid=auth.currentUser?.uid+usersList[position].uid
                this.frienduid=auth.currentUser?.uid
            }

           sentrequests.add(newfriend)

           FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(auth.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            exitChat()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.ref.child("sent_requests").setValue(sentrequests)
                        }
                    })

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


            startActivity(Intent(this, ChatListPage::class.java))

        }
        fun AddFriend(position: Int)
        {
            var friend_sentrequest:frienddata=frienddata().apply {
                this.frienduid=CurrentUser?.uid
                this.chatUid=CurrentUser!!.friend_requests[position].chatUid
            }
           // friendlist.add(CurrentUser!!.friend_requests[position])
           // friendrequestsendlist.remove(CurrentUser!!.friend_requests[position])
            //  CurrentUser!!.friend_requests.remove(CurrentUser!!.friend_requests[position])
            /* FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(auth.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            exitChat()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.ref.child("FriendListsUid").setValue(friendlist)
                        }
                    })*/
            /*
            FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(auth.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            exitChat()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.ref.child("friend_requests").setValue(CurrentUser!!.friend_requests)
                        }
                    })*/
            //CurrentUser?.find_UserByUid("")
            //Log.e("hahaha",CurrentUser?.find_UserByUid(CurrentUser!!.friend_requests[position].frienduid!!)?.Email)


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


           /* tempuser=CurrentUser!!.find_UserByUid(CurrentUser!!.friend_requests[position].frienduid!!).also {
                Log.e("hahaha","Zafar")
            }*/
            CurrentUser?.add_friendList(CurrentUser!!.friend_requests[position])
            CurrentUser?.delete_friendRequest(CurrentUser!!.friend_requests[position])
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
            val alreadyfriend:Boolean=fbuser?.uid in friendlist.map({item->item.frienduid})
            val requestsend:Boolean=fbuser?.uid in CurrentUser!!.sent_requests?.map({item->item.frienduid})
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
        private fun exitChat(){
            auth.signOut()
            startActivity(Intent(this,MainPage::class.java))
            finish()
        }
}

