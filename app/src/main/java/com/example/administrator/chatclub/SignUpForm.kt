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




class SignUpForm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)

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

            } else {

                var temp = UserAccount(signupemailinput.text.toString(), signupnameinput.text.toString(), signuppasswordinput.text.toString())
                MainPage.AccountData.add(temp)
                Log.e("hahaha", "${MainPage.AccountData.size}")
                val intent = Intent(this, MainPage::class.java)
                startActivity(intent)
            }
        }

    }
}
