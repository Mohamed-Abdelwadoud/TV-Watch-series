package com.example.tvwatchseries.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.data.model.TvShow
import com.example.tvwatchseries.data.source.remote.ApiClient
import com.example.tvwatchseries.data.source.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsRepository {
    private var apiService: ApiService? = null

    init {
        apiService = ApiClient.getRetrofitInstance().create(ApiService::class.java)
    }


    fun getDetailedTVShows(id: String): MutableLiveData<DetailedResponse> {
        val data: MutableLiveData<DetailedResponse> = MutableLiveData<DetailedResponse>()
        apiService?.getTVShowDetails(id)!!.enqueue(object : Callback<DetailedResponse?> {
            override fun onResponse(
                call: Call<DetailedResponse?>,
                response: Response<DetailedResponse?>
            ) {
                Log.d("RRRRRRRRRRRRRRRR", "onResponse: ")
                data.value = response.body()
            }

            override fun onFailure(call: Call<DetailedResponse?>, t: Throwable) {
                data.value = (null)
                Log.d("RRRRRRRRRRRRRRRR", "onFailure: ${t.message}")
            }
        })

        return data

    }
}