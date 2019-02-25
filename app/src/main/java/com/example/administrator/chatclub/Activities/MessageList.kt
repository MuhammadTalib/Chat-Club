package com.example.administrator.chatclub.Activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.administrator.chatclub.Adapters.MessageListAdapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Models.Message
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.chatUid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_message_list.*

class MessageList : BaseActivity(),ChildEventListener {

    /*
        /*
        lateinit var photoPath:String
        var REQUEST_TAKE_MESSAGE_PHOTO=100
        val REQ_CODE = 203

        var myFriendIndex=0
        var myIndexInMyFriendList=0
    */

        lateinit var auth: FirebaseAuth
        lateinit var chatRef: DatabaseReference
        var CurrentUser: Users? = null
        lateinit var chatMessages: ArrayList<Message>
        lateinit var myChatAdapter: MessageListAdapter


        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_message_list)

            myChatAdapter= MessageListAdapter(chatMessages)
            myMsgsList.adapter=myChatAdapter
            myMsgsList.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        }

        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }
    }
    /*

         /*  // myChatAdapter= MessageListAdapter(chatMessages)
           // myMsgsList.adapter =myChatAdapter
           // myMsgsList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)

            if(auth.currentUser == null){
                exitChat()
                return
            }else{
                FirebaseDatabase.getInstance().getReference("Chat_Lists")
                        .child(ChatListPage.chatUid ?: "")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                exitChat()
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                CurrentUser = snapshot.getValue(Users::class.java)
                                if(CurrentUser == null){
                                    exitChat()
                                }
                                else
                                {
                                    Log.e("hahaha","asd")
                                }
                            }

                        })
            }

            msgimagefly.setOnClickListener{
                MainPage.MessageImagecClicked = 1;
               // takePicture(REQUEST_TAKE_MESSAGE_PHOTO)
            }
            imagefromgallery.setOnClickListener{
                MainPage.MessageImagecClicked = 1;
               // takePictureFromGallery(203)
            }


            sendMsgBtn.setOnClickListener {
                val msgText = msgEt.text.toString()
                if(msgText.isEmpty()){
                    msgEt.error = "Please enter a message!"
                }else{
                    msgEt.error = null
                    sendMessage(Message().apply {
                        id=chatRef.push().key
                        this.msgText = msgText
                        //userId = auth.currentUser?.uid
                       // userName = myProfile?.fullName
                    })
                }
            /*
                val MSG=Message(msgEt.text.toString(),1)
                val MSG1=Message(msgEt.text.toString(),0)

                if(MainPage.MessageImagecClicked == 1)
                {
                    Log.e("bbb","is imaged")
                    MSG.message_image= MainPage.myBitmapImg
                    MSG.ImagedMessage=1
                    MSG1.message_image= MainPage.myBitmapImg
                    MSG1.ImagedMessage=1
                }

                    //MainPage.AccountData[MainPage.MyAccountIndex].FriendList[ChatList.friend].MessageArray.add(MSG)
                MainPage.AccountData[MainPage.MyAccountIndex].lastmessage=MSG.msgText
                for(i in  MainPage.AccountData.indices)
                {
                    //    if(MainPage.AccountData[i].Username==MainPage.AccountData[MainPage.MyAccountIndex].FriendList[ChatList.friend].Username)
                    //    {
                    //        myFriendIndex=i
                      //  }
                }
                   // for(i in  MainPage.AccountData[myFriendIndex].FriendList.indices)
                    {
                       // if(MainPage.AccountData[MainPage.MyAccountIndex].Username==MainPage.AccountData[myFriendIndex].FriendList[i].Username)
                       // {
                        //    myIndexInMyFriendList=i
                       // }
                    }


                Log.e("hold","My Friend Index $myFriendIndex")
                Log.e("hold","MyIndex $myIndexInMyFriendList")
                   // MainPage.AccountData[myFriendIndex].FriendList[myIndexInMyFriendList].MessageArray.add(MSG1)
    //
                msgEt.setText("")
                   // val lastPosition = MainPage.AccountData[MainPage.MyAccountIndex].FriendList[ChatList.friend].MessageArray.size-1
                  //  myMsgsList.adapter?.notifyItemInserted(lastPosition)
                  //  myMsgsList.scrollToPosition(lastPosition)*/
               }

            chatRef = FirebaseDatabase.getInstance().getReference("Chat_Lists/${ChatListPage.chatUid}")
            chatRef.addChildEventListener(this)

        }
       // fun takePictureFromGallery(mode:Int)
       // {
          //  val intent = Intent(Intent.ACTION_GET_CONTENT)
           // //intent.type = "image/* video/*"
           // startActivityForResult(intent, mode)
       // }
        /* fun takePicture(mode:Int)
         {
             val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
             if(intent.resolveActivity(packageManager)!=null)
             {
                 var photofile: File?=null
                 try{
                     photofile=createImageFile()
                 }catch (e: IOException){}
                 if(photofile!=null)
                 {
                     val photoUri: Uri?= FileProvider.getUriForFile(this,"$packageName.fileprovider",photofile)

                     intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                     startActivityForResult(intent,mode)
                 }
             }

         }
          override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
              if(requestCode==REQUEST_TAKE_MESSAGE_PHOTO&& resultCode== Activity.RESULT_OK)
              {
                  val myBitmap = BitmapFactory.decodeFile(photoPath)
                  MainPage.myBitmapImg=myBitmap
              }
              else if(requestCode==REQ_CODE && resultCode == Activity.RESULT_OK)
              {
                  val photoUri = data?.data
                  MainPage.myBitmapImg=photoUri as Any
              }

          }
           fun createImageFile(): File?
           {
               val fileName="MyPicture"
               val storagedir=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
               val image= File.createTempFile(fileName,".jpg",storagedir)
               photoPath=image.absolutePath

               return image
           }

           fun getImageUri(context: Context, inImage: Bitmap): Uri {
               val bytes = ByteArrayOutputStream()
               inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
               val path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null)
               return Uri.parse(path)
           }*/
       private fun sendMessage(msg:Message){
           chatRef.child(msg.id ?: "-").setValue(msg)
           msgEt.setText("")
       }
        private fun exitChat(){
            auth.signOut()
            startActivity(Intent(this,MainPage::class.java))
            finish()
        }
    */
      */*/*/*/
    lateinit var chatNodeRef: DatabaseReference
    var myProfile: Users? = null
    lateinit var chatMessages:ArrayList<Message>
    lateinit var myChatAdapter: MessageListAdapter
    private val PICK_IMG = 123
    lateinit var imgRef: StorageReference
     var imgUri: Uri=Uri.EMPTY
    lateinit var dbRef: DatabaseReference
    var imgStr:String=""
    var imageIsComing:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        chatMessages = arrayListOf()

        myChatAdapter = MessageListAdapter(this, chatMessages, Authentication.currentUser?.uid!!)
        imgRef = FirebaseStorage.getInstance().getReference("images")
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
            if(msgText.isEmpty()){
                msgEt.error = "Please enter a message!"
            }else{
                msgEt.error = null
                sendMessage(Message().apply {
                    id=chatNodeRef.push().key
                    messageText = msgText
                    userId = Authentication.currentUser?.uid
                    userName = myProfile?.Username
                    if(imageIsComing==1)
                    {
                        this.image="Not Null"
                    }
                    if(imageIsComing==2)
                    {
                        this.video="Not Null"
                    }

                })
            }
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
                if(imageIsComing==1)
                {
                    imageIsComing=0
                    uploadImage(msg.id ?: "")
                }
                else if(imageIsComing==2)
                {
                    imageIsComing=0
                    uploadVideo(msg.id ?: "")
                }

            } else {
                toast("There was an error, please try again")
              //  fbUser.delete()
            }
        }
        msgEt.setText("")
    }

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {

    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        val fbMsg = p0.getValue(Message::class.java)
        if(fbMsg!=null){
            Log.e("hahaha","not nullaaaaaaa")
            myChatAdapter.change(fbMsg)
        }

    }

    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        val fbMsg = p0.getValue(Message::class.java)
        Log.e("hahaha",fbMsg?.messageText)
        if(fbMsg!=null){
            Log.e("hahaha",fbMsg.messageText)
            myChatAdapter.add(fbMsg)
           // myMsgsList.scrollToPosition(myChatAdapter.itemCount-1)
        }
    }

    override fun onChildRemoved(p0: DataSnapshot) {

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imgUri = data.data!!
                if (imgUri.toString().contains("image")) {
                    Log.e("hahaha","imageeeeeeee")
                    imageIsComing=1
                } else  if (imgUri.toString().contains("video")) {
                    Log.e("hahaha","videoooooooo")
                    imageIsComing=2
                }
            }
        }
    }

    fun uploadImage(id: String) {

        if(imgUri!= Uri.EMPTY)
        {
            val task = imgRef.child(id + ".jpg").putFile(imgUri)
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    imgRef.child(id + ".jpg").downloadUrl.addOnCompleteListener { d ->
                        imgStr = d.result?.toString()!!
                        imgUri= Uri.EMPTY
                        chatNodeRef.child(id).child("image").setValue(imgStr)
                    }
                }
            }
        }
    }
    fun uploadVideo(id: String) {

        if(imgUri!= Uri.EMPTY)
        {
            val task = imgRef.child(id + ".mp4").putFile(imgUri)
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    imgRef.child(id + ".mp4").downloadUrl.addOnCompleteListener { d ->
                        imgStr = d.result?.toString()!!
                        imgUri= Uri.EMPTY
                        chatNodeRef.child(id).child("video").setValue(imgStr)
                    }
                }
            }
        }


    }
}