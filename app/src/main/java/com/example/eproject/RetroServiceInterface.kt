package com.example.eproject

import com.example.eproject.models.ProjectModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {
    @GET("upload.php/")
    fun getProjectList(): Call<List<ProjectModel>>
}