package com.example.administrator.chatclub.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.administrator.chatclub.Adapters.ChatListAdapter
import com.example.administrator.chatclub.Adapters.GroupListAdapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Models.GroupOfUsers
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.Models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_list_page.*

class ChatListPage :BaseActivity() {

    lateinit var friendlist:ArrayList<Users>
    lateinit var grouplist:ArrayList<GroupOfUsers>
    lateinit var chatuserAdapter: ChatListAdapter
    lateinit var GroupuserAdapter: GroupListAdapter

    companion object {
        var chatUid:String?=null
        var CurrentUser: Users? = null
        var ProfileUser: Users?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list_page)
        getSupportActionBar()?.hide()

        grouplist= arrayListOf()
        friendlist= arrayListOf()
        Authentication = FirebaseAuth.getInstance()

        /* if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
            userImage.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Int)
        else if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Bitmap)
            userImage.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Bitmap)
*/

        chatuserAdapter= ChatListAdapter(friendlist, ::openMessageList, ::openProfile)
        GroupuserAdapter= GroupListAdapter(grouplist, ::openMessageList)

        mygrouplist.adapter=GroupuserAdapter
        mygrouplist.layoutManager=LinearLayoutManager( this, LinearLayout.VERTICAL,false)


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
                               chatuserAdapter.addAll(CurrentUser?.GroupListUid?.map { u-> Users().apply {
                                   this.Username=u.Name
                                   this.Email="is_group"
                                   this.uid=u.uid
                               } } as ArrayList<Users>)

                                if(CurrentUser?.FriendListsUid?.size !=0) {
                                     for (i in CurrentUser!!.FriendListsUid) {
                                         var TempUser: Users?
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
                               /* if(CurrentUser?.GroupListUid?.size !=0) {
                                    for (i in CurrentUser!!.GroupListUid) {
                                        var TempUser:GroupOfUsers? = null
                                        FirebaseDatabase.getInstance().getReference("Chat_Users")
                                                .child(CurrentUser?.uid!!)
                                                .child("GroupListUid")
                                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                                    override fun onCancelled(p0: DatabaseError) {
                                                        //exitChat()
                                                    }

                                                    override fun onDataChange(snapshot: DataSnapshot) {
                                                        TempUser = snapshot.getValue(GroupOfUsers::class.java)
                                                        if(TempUser!=null)
                                                        {
                                                        }
                                                    }

                                                })
                                    }
                                }*/
                            }
                        }
                    })

        }

        userImage.setOnClickListener {
            ProfileUser = CurrentUser
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

        groupactivity.setOnClickListener {
            startActivity(Intent(this, GroupMaker::class.java))
        }

    }
    fun openMessageList(index:Int){

        if(index >= CurrentUser?.GroupListUid?.size!!)
            chatUid = CurrentUser!!.FriendListsUid[index- CurrentUser?.GroupListUid?.size!!].chatUid
        else
            chatUid =friendlist[index].uid

        var Intent=Intent(this, MessageList::class.java)
        startActivity(Intent)

    }
    fun openProfile(index:Int){
        if(index >= CurrentUser?.GroupListUid?.size!!) {
            ProfileUser =friendlist[index+ CurrentUser?.GroupListUid?.size!!]
            startActivity(Intent(this, Profile::class.java))
        }
    }

}
