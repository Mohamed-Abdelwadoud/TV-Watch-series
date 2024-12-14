package com.example.tvwatchseries.domain.repository

import com.example.tvwatchseries.domain.model.DetailedTvShowModel
import com.example.tvwatchseries.utility.NetWorkResponseResult
import kotlinx.coroutines.flow.Flow

interface ShowDetailsRepository {
   suspend fun getDetailedTVShows(id: String): Flow<NetWorkResponseResult<DetailedTvShowModel>>

}