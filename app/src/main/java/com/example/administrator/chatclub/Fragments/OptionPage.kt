package com.example.administrator.chatclub.Fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.administrator.chatclub.Base.FragmentBase
import com.example.administrator.chatclub.R
import com.example.administrator.chatclub.dialog.dialog


class OptionPage : FragmentBase() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view= inflater.inflate(R.layout.fragment_option_page, container, false)
        var logout_btn:TextView=view.findViewById(R.id.logout)
        logout_btn.setOnClickListener {
            val dialog =dialog(context as Activity,"Do You Really Want to Exit","Log Out","Go Back!",::okClicked,::canceltouched)
            dialog.setCancelable(true)
            dialog.show()
        }
        return view
    }
    private fun okClicked(){
        exitChat()
    }
    private fun canceltouched(){

    }
}
