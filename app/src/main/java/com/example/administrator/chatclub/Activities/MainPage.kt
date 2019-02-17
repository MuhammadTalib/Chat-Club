package com.example.administrator.chatclub.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPage : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        getSupportActionBar()?.hide()

        Authentication = FirebaseAuth.getInstance()

        if(Authentication.currentUser != null){
            startActivity(Intent(this, FragmentsHolder::class.java))
            finish()
        }
        login.setOnClickListener {

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
        signup.setOnClickListener{
            val intent= Intent(this, SignUpForm::class.java)
            startActivityForResult(intent,1)
        }

    }
    private fun signIn(email:String,password:String){
        showProgress()
        Authentication.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    hideProgress()
                    if(it.isSuccessful){
                        toast("Signed In!")
                        startActivity(Intent(this, FragmentsHolder::class.java))
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
