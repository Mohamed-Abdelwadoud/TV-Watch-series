package com.example.tvwatchseries.domain.repository

import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.utility.NetWorkResponseResult
import kotlinx.coroutines.flow.Flow

interface MostPopularTvShowsRepository {
    suspend fun getMostPopularTVShows(page: Int): Flow<NetWorkResponseResult<MostPopularModel>>

}