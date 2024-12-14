package com.example.tvwatchseries.data.source.remote

import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("most-popular")
    suspend fun getMostPopularTVShows(@Query("page") page: Int): MostPopularResponse


    @GET("show-details")
   suspend fun getTVShowDetails(@Query("q") tvShowId: String?): DetailedResponse

    @GET("search")
    suspend fun searchTVShow(@Query("q") query: String?, @Query("page") page: Int): MostPopularResponse

}