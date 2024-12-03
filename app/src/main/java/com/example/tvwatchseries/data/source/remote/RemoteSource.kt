package com.example.tvwatchseries.data.source.remote

import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import retrofit2.Call

interface RemoteSource {

    fun getMostPopularTVShows(page: Int): Call<MostPopularResponse?>?


    fun getTVShowDetails(tvShowId: String?): Call<DetailedResponse>?

    fun searchTVShow(query: String?, page: Int): Call<MostPopularResponse?>?

}