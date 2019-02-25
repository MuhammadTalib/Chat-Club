package com.example.administrator.chatclub

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CountriesAPI {

    @GET("/rest/v2")
    fun getCountries(): Call<ArrayList<CountriesResponse>>

}