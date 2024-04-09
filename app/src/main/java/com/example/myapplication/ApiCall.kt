package com.example.myapplication

import android.content.Context
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.provider.ContactsContract.Data
import android.widget.Toast
import retrofit2.Callback
import retrofit2.Response

//import retrofit2.

class ApiCall {
    fun getSomeData(context: Context, callback: (DataModel) -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/recipes/complexSearch")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<DataModel> = service.getData() // Assuming getData() is defined in ApiService

        call.enqueue(object : Callback<DataModel> {
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                if (response.isSuccessful) {
                    val data: DataModel? = response.body()
                    data?.let { callback(it) }
                } else {
                    Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
