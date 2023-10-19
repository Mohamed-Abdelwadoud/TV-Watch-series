package com.example.tvwatchseries.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.repository.MostPopularTvShowsRepository
import com.example.tvwatchseries.repository.SearchRepository

class SearchViewModel :ViewModel() {
    private var searchRepo: SearchRepository? = null

    init {
        searchRepo = SearchRepository()
    }

    fun getSearchTVShows(q:String,page:Int): MutableLiveData<MostPopularResponse>? {
        return searchRepo?.getSearchedShows(q,page)
    }
}