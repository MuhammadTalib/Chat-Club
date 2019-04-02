package com.example.administrator.chatclub

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.administrator.chatclub.Activities.MainPage
import com.example.administrator.chatclub.Models.GroupOfUsers
import com.example.administrator.chatclub.Models.Users
import com.example.administrator.chatclub.Models.usertempdata
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.random.Random

var auth: FirebaseAuth = FirebaseAuth.getInstance()
var chatUid:String?=null
var CurrUser: Users? = null
var ProfileUser: usertempdata?= null
var CurrGroup:GroupOfUsers?=null
var tempuser: Users?=null
var imgRef: StorageReference = FirebaseStorage.getInstance().getReference("images")
var imgUri: Uri=Uri.EMPTY
var imgStr:String=""
val PICK_IMG = 123
var linkpath:String?=null

fun uploadImage(id: String,databasepath:String) {

    if(imgUri!= Uri.EMPTY)
    {
        val task = imgRef.child(id + ".jpg").putFile(imgUri)
        task.addOnCompleteListener {
            if (it.isSuccessful) {
                imgRef.child(id + ".jpg").downloadUrl.addOnCompleteListener { d ->
                    imgStr = d.result?.toString()!!
                    imgUri= Uri.EMPTY
                    FirebaseDatabase.getInstance().getReference(databasepath).setValue(imgStr)
                }
            }
        }
    }

}
fun uploadVideo(id: String,chatNodeRef: DatabaseReference) {

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