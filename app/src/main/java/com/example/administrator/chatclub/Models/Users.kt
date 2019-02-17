package com.example.administrator.chatclub.Models

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Users
{
    private var tempuser: Users?=null
    var Email:String?=null
    var Username:String?=null
    //var password:String?=null
    var uid:String?=null
    var Country:String?=null
    var FriendListsUid=arrayListOf<frienddata>()
   // var totalGroups:Int=0
    var GroupListUid= arrayListOf<GroupOfUsers>()
    var friend_requests= arrayListOf<frienddata>()
    var sent_requests= arrayListOf<frienddata>()

    public fun add_friendList(friend: frienddata)
    {
        Log.e("hahaha","addfriend list")
        var friendlist= arrayListOf<frienddata>()

        this.FriendListsUid.add(friend)
        friendlist.addAll(this.FriendListsUid)

        Log.e("hahaha","added")
        FirebaseDatabase.getInstance().getReference("Chat_Users")
                .child(this.uid!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.child("FriendListsUid").setValue(friendlist)
                    }
                })
    }
    public fun add_GroupList(group: GroupOfUsers)
    {
        Log.e("hahaha","group list")
        var grouplist= arrayListOf<GroupOfUsers>()
        this.GroupListUid.add(group)
        grouplist.addAll(this.GroupListUid)

        FirebaseDatabase.getInstance().getReference("Chat_Users")
                .child(this.uid!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.child("GroupListUid").setValue(grouplist)
                    }
                })
    }

    public fun delete_friendRequest(friend: frienddata)
    {
        Log.e("hahaha","delete_friendRequest")
        var friendrequest= arrayListOf<frienddata>()

        if(this.friend_requests.contains(friend))
            this.friend_requests.remove(friend)

        friendrequest.addAll(this.friend_requests)
        FirebaseDatabase.getInstance().getReference("Chat_Users")
                .child(this.uid!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.child("friend_requests").setValue(friendrequest)
                    }
                })
    }

    public fun delete_sentRequest(friend: frienddata)
    {
        Log.e("hahaha","delete_sentRequest")
        var sentrequest= arrayListOf<frienddata>()

        var contain:Boolean=false
        var index:Int?=null
        for(i in this.sent_requests.indices)
        {
            if(this.sent_requests[i].frienduid==friend.frienduid && this.sent_requests[i].chatUid==friend.chatUid)
            {
                contain=true
                index=i
                break
            }

        }
        if(contain)
        {
            this.sent_requests.removeAt(index!!)
        }

        sentrequest.addAll(this.sent_requests)


        FirebaseDatabase.getInstance().getReference("Chat_Users")
                .child(this.uid!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.child("sent_requests").setValue(sentrequest)
                    }
                })
    }
    public fun find_UserByUid(uidd:String): Users
    {
        lateinit var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        Log.e("hahaha","finding")
        Log.e("hahaha",uidd)
        FirebaseDatabase.getInstance().getReference("Chat_Users")
                .child(uidd ?: "")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("hahaha","on Cancelled")
                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.e("hahaha","aaaaaaaaaaaaaaaaaaaOndataChanes")
                       // tempuser=snapshot.getValue(Users::class.java)
                    }
                })

        return tempuser!!
    }


   // var CoverPhoto:Any?=null
   // var userImage:Any?=null
   // var lastmessage:String="Say Hi to you new friend ${this.Username}"
   // var personalposts=ArrayList<post>()
   // var FriendList=ArrayList<UserAccount>()

   // var MessageArray= arrayListOf<Message>(Message("Say Hi to you new friend ${this.Username}",0))
}