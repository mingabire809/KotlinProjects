package com.example.eproject.register

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUser {
    private var retrofit: Retrofit? = null
    fun getUser(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}