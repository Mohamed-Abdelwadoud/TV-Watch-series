package com.example.tvwatchseries.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.domain.model.MostPopularModel

interface MostPopularTvShowsRepository {
    fun getMostPopularTVShows(page: Int): MutableLiveData<MostPopularModel>

}