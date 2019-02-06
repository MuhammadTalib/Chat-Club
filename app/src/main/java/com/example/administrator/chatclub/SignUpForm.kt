package com.example.administrator.chatclub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import android.view.MotionEvent
import android.widget.CheckBox
import android.widget.CompoundButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.chat_list_view.*


class SignUpForm : AppCompatActivity() {

    lateinit var Authentication:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)

        Authentication = FirebaseAuth.getInstance()

        var Countriesdata = ArrayList<Country>()
        Countriesdata.add(Country("Pakistan", R.drawable.pakistan))
        Countriesdata.add(Country("Iran", R.drawable.iran))
        Countriesdata.add(Country("China", R.drawable.china))
        Countriesdata.add(Country("Turkey", R.drawable.turkey))
        Countriesdata.add(Country("India", R.drawable.india))
        Countriesdata.add(Country("Bangladesh", R.drawable.bangladesh))


        var spinnerAdapter: SpinnerCustomDropdownAdapter = SpinnerCustomDropdownAdapter(this, Countriesdata)
        var spinner: Spinner = findViewById(R.id.CountrySpinner) as Spinner
        spinner.adapter = spinnerAdapter


        signuppasswordinput.setOnTouchListener(View.OnTouchListener { _ , _ ->

            signupemailinput.setText("${signupnameinput.text.toString().replace("\\s".toRegex(), "")}@chatclub.com".toLowerCase())
            false
        })


        checkbox.setOnCheckedChangeListener { _ , isChecked ->
            if (isChecked)
                checkboxerror.visibility = View.GONE
            else
                checkboxerror.visibility = View.VISIBLE
        }

        signupsignup.setOnClickListener {

            checkboxerror.visibility = View.GONE
            passworderror.visibility = View.GONE
            if (!checkbox.isChecked()) {
                checkboxerror.visibility = View.VISIBLE

                if (signuppasswordinput.text.toString().length < 6) {
                    passworderror.visibility = View.VISIBLE
                }

            }
            else
            {
                var temp = Users()
                var emailtext=signupemailinput.text.toString()
                var nametext=signupnameinput.text.toString()
                var passtext=signuppasswordinput.text.toString()
              /*//  MainPage.AccountData.add(temp)
                //var text:Long=CountrySpinner.selectedItem
               // Log.e("hahaha","${text}")
               // Log.e("hahaha","${Countriesdata[text.toInt()].name}")
              //  val intent = Intent(this, MainPage::class.java)
               // startActivity(intent)*/
                signIn(nametext,emailtext,passtext,"Pakistan")
            }

        }

    }
    private fun signIn(name:String,email:String,pass:String,Country:String)
    {
        showProgress()
        Authentication.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {

                    if(it.isSuccessful){
                        toast("Signed in!")
                        val fbUser = it.result?.user
                        if(fbUser != null){
                            FirebaseDatabase.getInstance()
                                    .getReference("Chat_Users")
                                    .child(fbUser.uid)
                                    .setValue(Users().apply {

                                       this.Email=email
                                        this.Username=name
                                        this.uid = fbUser.uid
                                        this.Country= Country

                                    }).addOnCompleteListener { dbTask ->
                                        hideProgress()
                                        if(dbTask.isSuccessful){
                                            startActivity(Intent(this,ChatList::class.java))
                                            finish()
                                        }else{
                                            toast("There was an error, please try again")
                                            fbUser.delete()
                                        }
                                    }
                        }else{
                            toast("There was an error, please try again")
                        }
                    }else{
                        hideProgress()
                        toast("Error : ${it.exception?.message}")
                    }
                }
    }
    private fun showProgress(){
        progressView.show()
        signupsignup.hide()
    }

    private fun hideProgress(){
        progressView.hide()
        signupsignup.show()
    }
}
