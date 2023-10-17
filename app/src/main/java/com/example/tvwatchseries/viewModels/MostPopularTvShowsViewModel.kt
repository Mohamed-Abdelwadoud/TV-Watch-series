package com.example.tvwatchseries.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.repository.MostPopularTvShowsRepository

class MostPopularTvShowsViewModel : ViewModel() {
    private var mostPopularTvShowsRepository: MostPopularTvShowsRepository? = null

    init {
        mostPopularTvShowsRepository = MostPopularTvShowsRepository()
    }

    fun getMostPopTvShows(page:Int): MutableLiveData<MostPopularResponse>? {
         return mostPopularTvShowsRepository?.getMostPopularTVShows(page)
    }




}