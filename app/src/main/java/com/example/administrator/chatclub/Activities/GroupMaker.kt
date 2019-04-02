package com.example.administrator.chatclub.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.administrator.chatclub.Adapters.UserListAdapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.CurrGroup
import com.example.administrator.chatclub.Models.GroupOfUsers
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.Models.usertempdata
import com.example.administrator.chatclub.tempuser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_group_maker.*

class GroupMaker : BaseActivity(), ChildEventListener {


    lateinit var userRef: DatabaseReference
    var CurrentUser: Users? = null
    lateinit var auth: FirebaseAuth
    lateinit var UserAdapter: UserListAdapter
    lateinit var usersList: ArrayList<Users> //all user list
    lateinit var GroupList: ArrayList<Users>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_maker)
        getSupportActionBar()?.hide()

        usersList = arrayListOf()
        GroupList= arrayListOf()

        auth = FirebaseAuth.getInstance()
        UserAdapter= UserListAdapter(usersList, ::OnCheckboxChecked, ::OnCheckboxUnChecked)

        list.adapter = UserAdapter
        list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

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
                            Log.e("hahaha","Current User=${CurrentUser?.username}")
                            if (CurrentUser == null) {
                                exitChat()
                            }
                            else
                                GroupList.add(CurrentUser!!)

                        }
                    })
        }
        group_done.setOnClickListener {
            var uid= FirebaseDatabase.getInstance().getReference("Chat_Users/${CurrentUser?.uid}/GroupListUid").push().key!!
            CurrGroup=GroupOfUsers().apply {
                this.Name = GroupList.map { u->u.username }.joinToString(",")
                this.uid=uid
                for (i in GroupList.indices)
                {
                    this.UsersList.add(usertempdata().apply {
                        this.Username=GroupList[i].username
                        this.Email=GroupList[i].Email
                        this.uid=GroupList[i].uid!!
                        this.chatid=uid
                    })
                }
            }
             for(i in GroupList)
             {
                 FirebaseDatabase.getInstance().getReference("Chat_Users/${com.example.administrator.chatclub.auth.currentUser?.uid!!}/sent_requests").push().key!!
                 FirebaseDatabase.getInstance()
                        .getReference("Chat_Users")
                         .child(i.uid!!)
                         .child("GroupListUid")
                         .child(uid)
                         .setValue(CurrGroup)}

            startActivity(Intent(this, GroupActivity::class.java))

        }
        goback.setOnClickListener {
            startActivity(Intent(this, FragmentsHolder::class.java))
        }

        userRef = FirebaseDatabase.getInstance().getReference("Chat_Users")
        userRef.addChildEventListener(this)

    }
    fun OnCheckboxChecked(index:Int)
    {
        GroupList.add(usersList[index])
        if(GroupList.size>1)
        {
            group_done.visibility= View.VISIBLE
        }
    }

    fun OnCheckboxUnChecked(index:Int)
    {
        GroupList.remove(usersList[index])
        if(GroupList.size<2)
        {
            group_done.visibility=View.GONE
        }
    }
    override fun onCancelled(p0: DatabaseError) {
    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        val fbuser = p0.getValue(Users::class.java)
        if (fbuser != null && fbuser.uid != auth.currentUser?.uid)
        {
            usersList.add(fbuser)
            UserAdapter.notifyItemChanged(usersList.size-1)
            Log.e("hahaha","userrr=${fbuser.username}")
        }
    }

    override fun onChildRemoved(p0: DataSnapshot) {
    }


}
