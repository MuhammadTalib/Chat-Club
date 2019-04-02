package com.example.administrator.chatclub.Activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.administrator.chatclub.*
import com.example.administrator.chatclub.Adapters.MessageListAdapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Models.Message
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_message_list.*

class MessageList : BaseActivity(),ChildEventListener {

    lateinit var chatNodeRef: DatabaseReference
    var myProfile: Users? = null
    lateinit var chatMessages:ArrayList<Message>
    lateinit var myChatAdapter: MessageListAdapter

    lateinit var dbRef: DatabaseReference
    var imageIsComing:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        chatMessages = arrayListOf()

        myChatAdapter = MessageListAdapter(this, chatMessages, Authentication.currentUser?.uid!!)

        dbRef = FirebaseDatabase.getInstance().getReference("users")

        myMsgsList.adapter = myChatAdapter
        myMsgsList.layoutManager = LinearLayoutManager(this)

        if(Authentication.currentUser == null){
            exitChat()
            return
        }else{
            FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(Authentication.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                            exitChat()
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            myProfile = snapshot.getValue(Users::class.java)
                            if(myProfile == null){
                                exitChat()
                            }
                        }

                    })
        }


        chatNodeRef = FirebaseDatabase.getInstance().getReference("chats/${chatUid}")
        chatNodeRef.addChildEventListener(this)


        sendMsgBtn.setOnClickListener {
            val msgText = msgEt.text.toString()
            msgEt.error = null
            sendMessage(Message().apply {
                id=chatNodeRef.push().key
                messageText = msgText
                userId = Authentication.currentUser?.uid
                userName = myProfile?.username
                if(imageIsComing==1) {
                    this.image="Not Null"
                }
                if(imageIsComing==2) {
                    this.video="Not Null"
                }
            })
        }
        imagefromgallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/* video/*")
            startActivityForResult(Intent.createChooser(intent, "Select Imaage"), PICK_IMG)
        }

    }

    private fun sendMessage(msg: Message){
        chatNodeRef.child(msg.id ?: "-").setValue(msg).addOnCompleteListener { dbTask ->
            if (dbTask.isSuccessful) {
                if(imageIsComing==1) {
                    imageIsComing=0
                    uploadImage(msg.id ?: "","chats/${chatUid}/${msg.id}/image")

                }
                else if(imageIsComing==2) {
                    imageIsComing=0
                    uploadVideo(msg.id ?: "",chatNodeRef)
                }
            } else {
                toast("There was an error, please try again")
            }
        }
        msgEt.setText("")
    }

    override fun onCancelled(p0: DatabaseError) {}
    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        val fbMsg = p0.getValue(Message::class.java)
        if(fbMsg!=null){
            Log.e("hahaha","not nullaaaaaaa")
            myChatAdapter.change(fbMsg)}}
    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        val fbMsg = p0.getValue(Message::class.java)
        Log.e("hahaha",fbMsg?.messageText)
        if(fbMsg!=null){
            Log.e("hahaha",fbMsg.messageText)
            myChatAdapter.add(fbMsg)
           // myMsgsList.scrollToPosition(myChatAdapter.itemCount-1)
        }
    }
    override fun onChildRemoved(p0: DataSnapshot) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imgUri = data.data!!
                if (imgUri.toString().contains("image")) {
                    imageIsComing=1
                } else  if (imgUri.toString().contains("video")) {
                    imageIsComing=2
                }
            }
        }
    }


}