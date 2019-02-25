package com.example.administrator.chatclub.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.chatclub.R

class UserProfile: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_user_profile, container, false)
       // var UserNmae:TextView=view.findViewById(R.id.MyUserName)
       // UserNmae.text= ProfileUser?.Username
      //  return wvie
    }
}
