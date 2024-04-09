package com.example.myapplication

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{id}")
    fun getData(): Call<DataModel> //TODO: define datamodel
    
    val retrofit: Retrofit
}