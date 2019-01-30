package com.example.administrator.chatclub

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_message_list.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class MessageList : AppCompatActivity() {


    lateinit var photoPath:String
    var REQUEST_TAKE_MESSAGE_PHOTO=100
    val REQ_CODE = 203

    var myFriendIndex=0
    var myIndexInMyFriendList=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)



        myMsgsList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        myMsgsList.adapter = MessageListAdapter(MainPage.AccountData[MainPage.MyAccountIndex].FriendList[ChatList.friend].MessageArray)


        msgimagefly.setOnClickListener{
            MainPage.MessageImagecClicked = 1;
            takePicture(REQUEST_TAKE_MESSAGE_PHOTO)
        }
        imagefromgallery.setOnClickListener{
            MainPage.MessageImagecClicked = 1;
            takePictureFromGallery(203)
        }


            sendMsgBtn.setOnClickListener {

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

                MainPage.AccountData[MainPage.MyAccountIndex].FriendList[ChatList.friend].MessageArray.add(MSG)
                MainPage.AccountData[MainPage.MyAccountIndex].lastmessage=MSG.msgText
                for(i in  MainPage.AccountData.indices)
                {
                    if(MainPage.AccountData[i].Username==MainPage.AccountData[MainPage.MyAccountIndex].FriendList[ChatList.friend].Username)
                    {
                        myFriendIndex=i
                    }
                }
                for(i in  MainPage.AccountData[myFriendIndex].FriendList.indices)
                {
                    if(MainPage.AccountData[MainPage.MyAccountIndex].Username==MainPage.AccountData[myFriendIndex].FriendList[i].Username)
                    {
                        myIndexInMyFriendList=i
                    }
                }


                Log.e("hold","My Friend Index $myFriendIndex")
                Log.e("hold","MyIndex $myIndexInMyFriendList")
                MainPage.AccountData[myFriendIndex].FriendList[myIndexInMyFriendList].MessageArray.add(MSG1)

                msgEt.setText("")
                val lastPosition = MainPage.AccountData[MainPage.MyAccountIndex].FriendList[ChatList.friend].MessageArray.size-1
                myMsgsList.adapter?.notifyItemInserted(lastPosition)
                myMsgsList.scrollToPosition(lastPosition)
           }




    }
    fun takePictureFromGallery(mode:Int)
    {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/* video/*"
        startActivityForResult(intent, mode)
    }
    fun takePicture(mode:Int)
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
    }



}
