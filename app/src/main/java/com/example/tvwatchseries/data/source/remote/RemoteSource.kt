package com.example.tvwatchseries.data.source.remote

import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface RemoteSource {

    suspend fun getMostPopularTVShows(page: Int): Flow<MostPopularResponse>


    suspend fun getTVShowDetails(tvShowId: String?): Flow<DetailedResponse>

    suspend fun searchTVShow(query: String?, page: Int): Flow<MostPopularResponse>

}