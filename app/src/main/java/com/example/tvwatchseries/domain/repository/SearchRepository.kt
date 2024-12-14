package com.example.tvwatchseries.domain.repository

import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.utility.NetWorkResponseResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchedShows(name:String, page: Int ): Flow<NetWorkResponseResult<MostPopularModel>>

}