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
    var username:String?=null
    var uid:String?=null
    var profilepic:String?=null
    var coverpic:String?=null
    var Country:String?=null
}