package com.example.tvwatchseries.data.source.remote

import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import retrofit2.Call
import javax.inject.Inject

class ApiClient@Inject constructor(private val apiService: ApiService)  :
    RemoteSource {

    override fun getMostPopularTVShows(page: Int): Call<MostPopularResponse?>? {
        return apiService.getMostPopularTVShows(page)
    }

    override fun getTVShowDetails(tvShowId: String?): Call<DetailedResponse>? {
        return apiService.getTVShowDetails(tvShowId)
    }

    override fun searchTVShow(query: String?, page: Int): Call<MostPopularResponse?>? {
        return apiService.searchTVShow(query, page)
    }

}