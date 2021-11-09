package com.example.eproject

import android.net.DnsResolver
import android.telecom.Call
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eproject.models.ProjectModel
import retrofit2.Response
import javax.security.auth.callback.Callback


class ProjectViewModel: ViewModel() {

    var liveDataList: MutableLiveData<List<ProjectModel>>
       init {
           liveDataList = MutableLiveData()
       }
    fun getLiveDataObserver(): MutableLiveData<List<ProjectModel>>{
        return liveDataList
    }

    fun getProject(){
        val projectRetroInstance = RetroInstance.getRetroInstance()
        val projectRetroService = projectRetroInstance.create(RetroServiceInterface::class.java)
        val projectCall = projectRetroService.getProjectList()
        projectCall.enqueue(object : retrofit2.Callback<List<ProjectModel>> {
            override fun onFailure(call: retrofit2.Call<List<ProjectModel>>, t: Throwable) {
                liveDataList.postValue(null)
                Log.i("data", "Failed ${t.message}")
            }

            override fun onResponse(
                call: retrofit2.Call<List<ProjectModel>>,
                response: Response<List<ProjectModel>>
            ) {
                liveDataList.postValue(response.body())
                Log.i("data", "Success")
            }
        })

    }
}



