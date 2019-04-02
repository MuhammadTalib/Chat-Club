package com.example.administrator.chatclub.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.media.session.IMediaControllerCallback
import android.support.v4.widget.CircularProgressDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.administrator.chatclub.*
import com.example.administrator.chatclub.Base.FragmentBase
import com.example.administrator.chatclub.Models.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfile:FragmentBase() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view= inflater.inflate(R.layout.fragment_user_profile, container, false)
        var Profile:RelativeLayout=view.findViewById(R.id.profile)
        var UserName:TextView=view.findViewById(R.id.MyUserName)
        var UserNameE:EditText=view.findViewById(R.id.MyUserNameE)
        var ProfilePic:ImageView=view.findViewById(R.id.myProfileImage)
        var CoverPic:ImageView=view.findViewById(R.id.myCoverphoto)

        var EditProfilePic:ImageView=view.findViewById(R.id.editprofilepic)
        var EditCoverPic:Button=view.findViewById(R.id.editCoverPhoto)

        if (auth.currentUser == null) {
            exitChat()
        }
        else{
            FirebaseDatabase.getInstance().getReference("Chat_Users")
                    .child(auth.currentUser?.uid ?: "")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            exitChat()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            CurrUser = snapshot.getValue(Users::class.java)
                            UserName.text= CurrUser?.username
                            if(CurrUser?.profilepic!=null && CurrUser?.profilepic!= imgStr)
                            {
                                Log.e("hahaha", CurrUser?.profilepic)
                                Glide.with(context!!).applyDefaultRequestOptions(RequestOptions())
                                        .load(CurrUser?.profilepic).into(ProfilePic)

                            }
                            if(CurrUser?.coverpic!=null &&   CurrUser?.coverpic!= imgStr)
                            {
                                Glide.with(context!!).applyDefaultRequestOptions(RequestOptions())
                                        .load(CurrUser?.coverpic).into(CoverPic)
                            }
                        }
                    })
        }
        EditProfilePic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "Select Imaage"), PICK_IMG)
        }
        EditCoverPic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "Select Imaage"), 124)
        }
        UserName.setOnClickListener{
            UserName.visibility=View.GONE
            UserNameE.visibility=View.VISIBLE
            UserNameE.setText(UserName.text)
        }
        Profile.setOnClickListener {
            if(UserNameE.visibility==View.VISIBLE)
            {
                UserName.visibility=View.VISIBLE
                UserName.text=UserNameE.text
                UserNameE.visibility=View.GONE
                FirebaseDatabase.getInstance().getReference("Chat_Users/${auth.currentUser?.uid ?: ""}/username").setValue(UserName.text.toString())
            }
        }
        
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imgUri = data.data!!
                var uid= FirebaseDatabase.getInstance().getReference("Chat_Users").push().key
                myProfileImage.setImageURI(imgUri)
                uploadImage("${CurrUser?.username}/${uid}","Chat_Users/${auth.currentUser?.uid ?: ""}/profilepic")
            }
        }
        else if (requestCode == 124 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imgUri = data.data!!
                myCoverphoto.setImageURI(imgUri)
                uploadImage("${CurrUser?.username}/${CurrUser?.uid}","Chat_Users/${auth.currentUser?.uid ?: ""}/coverpic")
            }
        }
    }
}
