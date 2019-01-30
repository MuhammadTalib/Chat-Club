package com.example.administrator.chatclub

import android.content.Context
import android.content.Intent
import android.drm.DrmStore
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.util.Log
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPage : AppCompatActivity() {

    companion object
    {
        var MessageImagecClicked = 0
        var myBitmapImg:Any=0
        var MyAccountIndex:Int=0
        var AccountData = ArrayList<UserAccount>()
        var Posts=ArrayList<post>()
    }

    var found:Int=0
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        getSupportActionBar()?.hide()

        login.setOnClickListener() {


                for (i in AccountData.indices) {
                    if ((AccountData[i].Email==emailinput.text.toString() || AccountData[i].Username==emailinput.text.toString()) && AccountData[i].password==passwordinput.text.toString()) {
                        found = 1;
                        MyAccountIndex=i
                    }
                }
                if (found == 0) {
                    error.text ="**User Name or Password Incorrect"
                }
                else{
                    val intent= Intent(this,ChatList::class.java)
                    startActivity(intent)
                }


        }


        signup.setOnClickListener() {

            val intent= Intent(this,SignUpForm::class.java)
            startActivityForResult(intent,1)
        }

    }

}
