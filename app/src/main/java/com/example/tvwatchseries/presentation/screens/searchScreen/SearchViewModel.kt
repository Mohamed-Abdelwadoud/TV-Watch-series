package com.example.tvwatchseries.presentation.screens.searchScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.data.repository.SearchRepositoryImp
import com.example.tvwatchseries.data.source.remote.ApiClient
import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.usecases.SearchUseCase

class SearchViewModel ():ViewModel() {
     val searchUseCase= SearchUseCase(SearchRepositoryImp(ApiClient()))
//    private var searchRepo: SearchRepositoryImp? = null
//
//    init {
//        searchRepo = SearchRepositoryImp()
//    }

    fun getSearchTVShows(q:String,page:Int): MutableLiveData<MostPopularModel> {
        return searchUseCase.getSearchedShows(q,page)
    }
}