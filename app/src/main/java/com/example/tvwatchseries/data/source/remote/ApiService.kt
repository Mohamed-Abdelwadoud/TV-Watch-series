package com.example.tvwatchseries.data.source.remote

import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.data.model.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("most-popular")
    fun getMostPopularTVShows(@Query("page") page: Int): Call<MostPopularResponse?>?


    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId: String?): Call<DetailedResponse>?

}