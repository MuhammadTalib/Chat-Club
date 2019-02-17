package com.example.administrator.chatclub.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.R
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : BaseActivity() {

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
