package com.example.retrofitapp

import com.example.retrofitapp.api.CatJson
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("/fact?max_length=50")
    fun getCatFact():Call<CatJson>
}