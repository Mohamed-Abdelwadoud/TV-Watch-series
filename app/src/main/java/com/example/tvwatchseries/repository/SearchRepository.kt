package com.example.tvwatchseries.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.data.source.remote.ApiClient
import com.example.tvwatchseries.data.source.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {
    private var apiService: ApiService? = null

    init {
        apiService = ApiClient.getRetrofitInstance().create(ApiService::class.java)
    }


    fun getSearchedShows(q:String,page: Int ): MutableLiveData<MostPopularResponse> {
        val data: MutableLiveData<MostPopularResponse> = MutableLiveData<MostPopularResponse>()
        apiService?.searchTVShow(q,page)!!.enqueue(object : Callback<MostPopularResponse?> {
            override fun onResponse(
                call: Call<MostPopularResponse?>,
                response: Response<MostPopularResponse?>

            ) {
                data.value = response.body()
            }
            override fun onFailure(call: Call<MostPopularResponse?>, t: Throwable) {
                data.value = (null)
            }
        })

        return data

    }
}