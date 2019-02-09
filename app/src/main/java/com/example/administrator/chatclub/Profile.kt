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
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.chat_list_view.view.*
import kotlinx.android.synthetic.main.post_inage_view.*
import kotlinx.android.synthetic.main.post_view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class Profile : AppCompatActivity() {

   // var post=post(MainPage.AccountData[MainPage.MyAccountIndex])
    //lateinit var photoPath:String

    //lateinit var  selectedImage:Uri
   // val REQUEST_TAKE_PROFILE_PHOTO=1
   // val REQUEST_TAKE_COVER_PHOTO=2
   // val REQUEST_TAKE_POST_IMAGE=3
   // val RESULT_LOAD_IMAGE=4;
    companion object {
        var MyImageBitmap:Any=0;
        var CameraStatus = 0;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        /*MyUserName.text=MainPage.AccountData[MainPage.MyAccountIndex].Username

         if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
             myCoverphoto.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].CoverPhoto as Int)
         else if(MainPage.AccountData[MainPage.MyAccountIndex].CoverPhoto is Bitmap)
             myCoverphoto.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].CoverPhoto as Bitmap)


         if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Int)
             myProfileImage.setImageResource(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Int)
         else if(MainPage.AccountData[MainPage.MyAccountIndex].userImage is Bitmap)
             myProfileImage.setImageBitmap(MainPage.AccountData[MainPage.MyAccountIndex].userImage as Bitmap)

        // MyProfilepostList.layoutManager = LinearLayoutManager( this, LinearLayout.VERTICAL,false)
        // MyProfilepostList.adapter = PostListAdapter(MainPage.AccountData[MainPage.MyAccountIndex].personalposts,::onItemClick,::onButtonClick)
 */

        fly.setOnClickListener{
           // post.postContent=postfly.text.toString()
           // MainPage.AccountData[MainPage.MyAccountIndex].personalposts.add(post)
            postfly.hint="Whats on your mind"

        }
        camera.setOnClickListener{

            //takePicture(REQUEST_TAKE_POST_IMAGE)
           // MainPage.AccountData[MainPage.MyAccountIndex].personalposts.add(post)
          //  CameraStatus=1
        }
        editpen.setOnClickListener{
           // takePicture(REQUEST_TAKE_PROFILE_PHOTO)
        }//
        editCoverPhoto.setOnClickListener {
          //  takePicture(REQUEST_TAKE_COVER_PHOTO)
        }

    }
    fun onItemClick(position:Int){
        t("clicked on $position")
    }
    fun onButtonClick(position: Int)
    {
        //selectedImage=2
       // ShareGallery()
    }/*
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
                val photoUri: Uri?= FileProvider.getUriForFile(this,"$packageName.fileprovider",
                        photofile
                )

                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                startActivityForResult(intent,mode)
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQUEST_TAKE_PROFILE_PHOTO&& resultCode== Activity.RESULT_OK)
        {
            val myBitmap = BitmapFactory.decodeFile(photoPath)
            myProfileImage.setImageBitmap(myBitmap)
            MainPage.AccountData[MainPage.MyAccountIndex].userImage=myBitmap
        }
        if(requestCode==REQUEST_TAKE_COVER_PHOTO&& resultCode== Activity.RESULT_OK)
        {
            val myBitmap = BitmapFactory.decodeFile(photoPath)
            myCoverphoto.setImageBitmap(myBitmap)
            MainPage.AccountData[MainPage.MyAccountIndex].CoverPhoto=myBitmap
        }
        if(requestCode==REQUEST_TAKE_POST_IMAGE&& resultCode== Activity.RESULT_OK)
        {
            val myBitmap = BitmapFactory.decodeFile(photoPath)

            post.postImage=myBitmap
            Profile.MyImageBitmap = myBitmap
            //postimage.setImageBitmap(myBitmap)
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
           // selectedImage = data.getData();
            ShareGallery()
            //image.setImageURI(selectedImage)
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
    fun ShareGallery() {

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.setType("image/jpeg")
        shareIntent.putExtra(Intent.EXTRA_STREAM, selectedImage)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing My Application Image")
        startActivity(Intent.createChooser(shareIntent, "Share from"))
    }*/
}
