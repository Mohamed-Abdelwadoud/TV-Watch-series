package com.example.tvwatchseries.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.domain.model.DetailedTvShowModel

interface ShowDetailsRepository {
    fun getDetailedTVShows(id: String): MutableLiveData<DetailedTvShowModel>

}