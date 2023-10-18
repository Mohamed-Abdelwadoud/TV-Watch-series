package com.example.tvwatchseries.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.TvShow
import com.example.tvwatchseries.repository.MostPopularTvShowsRepository
import com.example.tvwatchseries.repository.ShowDetailsRepository

class DetailedTVViewModel :ViewModel() {
    private var showDetailsRepository: ShowDetailsRepository? = null

    init {
        showDetailsRepository = ShowDetailsRepository()
    }

    fun getTVDetails(id:String) : MutableLiveData<DetailedResponse>?{
       return showDetailsRepository?.getDetailedTVShows(id)
    }
}