package com.example.administrator.chatclub.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import com.example.administrator.chatclub.*
import com.example.administrator.chatclub.Adapters.SpinnerCustomDropdownAdapter
import com.example.administrator.chatclub.Base.BaseActivity
import com.example.administrator.chatclub.Models.Country
import com.example.administrator.chatclub.Models.Users
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit


class SignUpForm : BaseActivity(),retrofit2.Callback<ArrayList<CountriesResponse>> {


    var CountriesData: ArrayList<CountriesResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)


        CountriesData.clear()
        Api().service.getCountries().enqueue(this)

        var Countriesdata = ArrayList<Country>()
        Countriesdata.add(Country("Pakistan", R.drawable.pakistan))
        Countriesdata.add(Country("Iran", R.drawable.iran))
        Countriesdata.add(Country("China", R.drawable.china))
        Countriesdata.add(Country("Turkey", R.drawable.turkey))
        Countriesdata.add(Country("India", R.drawable.india))
        Countriesdata.add(Country("Bangladesh", R.drawable.bangladesh))
        Countriesdata.add(Country("Iran", R.drawable.iran))
        Countriesdata.add(Country("China", R.drawable.china))
        Countriesdata.add(Country("Turkey", R.drawable.turkey))
        Countriesdata.add(Country("India", R.drawable.india))
        Countriesdata.add(Country("Bangladesh", R.drawable.bangladesh))
        Countriesdata.add(Country("Pakistan", R.drawable.pakistan))
        Countriesdata.add(Country("Iran", R.drawable.iran))
        Countriesdata.add(Country("China", R.drawable.china))
        Countriesdata.add(Country("Turkey", R.drawable.turkey))
        Countriesdata.add(Country("India", R.drawable.india))
        Countriesdata.add(Country("Bangladesh", R.drawable.bangladesh))
        Countriesdata.add(Country("Iran", R.drawable.iran))
        Countriesdata.add(Country("China", R.drawable.china))
        Countriesdata.add(Country("Turkey", R.drawable.turkey))
        Countriesdata.add(Country("India", R.drawable.india))
        Countriesdata.add(Country("Bangladesh", R.drawable.bangladesh))


        var spinnerAdapter: SpinnerCustomDropdownAdapter = SpinnerCustomDropdownAdapter(this, CountriesData)
        var spinner: Spinner = findViewById(R.id.CountrySpinner) as Spinner
        spinner.adapter = spinnerAdapter


        signuppasswordinput.setOnTouchListener(View.OnTouchListener { _, _ ->

            signupemailinput.setText("${signupnameinput.text.toString().replace("\\s".toRegex(), "")}@chatclub.com".toLowerCase())
            false
        })


        checkbox.setOnCheckedChangeListener { _, isChecked ->
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
                var emailtext = signupemailinput.text.toString()
                var nametext = signupnameinput.text.toString()
                var passtext = signuppasswordinput.text.toString()
                signIn(nametext, emailtext, passtext, "Pakistan")
            }

        }

    }

    override fun onFailure(call: Call<ArrayList<CountriesResponse>>, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResponse(call: Call<ArrayList<CountriesResponse>>, response: Response<ArrayList<CountriesResponse>>) {
        val myJob = response.body()
        myJob?.also { c ->
            c.also {
                CountriesData.addAll(it.toList())
            }
        }
    }


    private fun signIn(name: String, email: String, pass: String, Country: String) {
        showProgress()
        Authentication.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        toast("Signed in!")
                        val fbUser = it.result?.user
                        if (fbUser != null) {
                            FirebaseDatabase.getInstance()
                                    .getReference("Chat_Users")
                                    .child(fbUser.uid)
                                    .setValue(Users().apply {

                                        this.Email = email
                                        this.Username = name
                                        this.uid = fbUser.uid
                                        this.Country = Country

                                    }).addOnCompleteListener { dbTask ->
                                        hideProgress()
                                        if (dbTask.isSuccessful) {
                                            startActivity(Intent(this, FragmentsHolder::class.java))
                                            finish()
                                        } else {
                                            toast("There was an error, please try again")
                                            fbUser.delete()
                                        }
                                    }
                        } else {
                            toast("There was an error, please try again")
                        }
                    } else {
                        hideProgress()
                        toast("Error : ${it.exception?.message}")
                    }
                }
    }

    private fun showProgress() {
        progressView.show()
        signupsignup.hide()
    }

    private fun hideProgress() {
        progressView.hide()
        signupsignup.show()
    }


}

