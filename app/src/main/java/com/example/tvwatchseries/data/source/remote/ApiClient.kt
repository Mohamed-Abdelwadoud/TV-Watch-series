package com.example.tvwatchseries.data.source.remote

import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import javax.inject.Inject

class ApiClient @Inject constructor(private val apiService: ApiService) :
    RemoteSource {

    override suspend fun getMostPopularTVShows(page: Int): Flow<MostPopularResponse> {
        return flowOf(apiService.getMostPopularTVShows(page))
    }


    override suspend fun getTVShowDetails(tvShowId: String?): Flow<DetailedResponse> {
        return flowOf(apiService.getTVShowDetails(tvShowId))
    }

    override suspend fun searchTVShow(query: String?, page: Int): Flow<MostPopularResponse> {
        return flowOf(apiService.searchTVShow(query, page))
    }

}