package com.example.eproject

import com.example.eproject.models.API
import com.example.eproject.register.RetrofitUser
import com.example.eproject.register.myApi

object Common {
    val BASE_URL = "https://myeproject.rf.gd/EProject/"
    val api:myApi
    get() = RetrofitUser.getUser(BASE_URL).create(myApi::class.java)
}