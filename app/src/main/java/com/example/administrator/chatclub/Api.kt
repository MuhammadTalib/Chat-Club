package com.example.administrator.chatclub

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.eu")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(CountriesAPI::class.java)
}