package com.example.tvwatchseries.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.domain.model.MostPopularModel

interface SearchRepository {
    fun getSearchedShows(name:String,page: Int ): MutableLiveData<MostPopularModel>

}