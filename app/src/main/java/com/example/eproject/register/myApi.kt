package com.example.eproject.register

import com.example.eproject.models.API
import okhttp3.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface myApi {
    @FormUrlEncoded
    @POST("signup.php")
    fun registering(@Field("first_name") first_name: String,@Field("last_name") last_name: String,@Field("email") email: String,@Field("phone_number") phone_number: String,@Field("password") password: String):retrofit2.Call<API>

    @FormUrlEncoded
    @POST("login.php")
    fun logging_in(@Field("email") email: String,@Field("password") password: String):retrofit2.Call<API>
}