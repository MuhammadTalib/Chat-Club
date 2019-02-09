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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPage : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
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

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            startActivity(Intent(this,ChatListPage::class.java))
            finish()
        }

        login.setOnClickListener() {

            var emailText:String=emailinput.text.toString()
            var passText:String=passwordinput.text.toString()

            var isError = false

            emailinput.error = null
            passwordinput.error = null

            if(emailText.isEmpty()){
                emailinput.error = "Please enter email"
                isError = true
            }

            if(passText.isEmpty()){
                passwordinput.error = "Please enter email"
                isError = true
            }

            if(!isError){
                signIn(emailText,passText)
            }

        }


        signup.setOnClickListener() {

            val intent= Intent(this,SignUpForm::class.java)
            startActivityForResult(intent,1)
        }


    }
    private fun signIn(email:String,password:String){
        showProgress()
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    hideProgress()
                    if(it.isSuccessful){
                        toast("SIGNED IN!")
                        startActivity(Intent(this,ChatListPage::class.java))
                        finish()
                    }else{
                        toast("Error : ${it.exception?.message}")
                    }
                }

    }

    private fun showProgress(){
        progressView1.show()
        login.hide()
    }

    private fun hideProgress(){
        progressView1.hide()
        login.show()
    }

}
