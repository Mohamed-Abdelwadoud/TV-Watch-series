package com.example.tvwatchseries.presentation.screens.searchScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    fun getSearchTVShows(q: String, page: Int): MutableLiveData<MostPopularModel> {
        return searchUseCase.getSearchedShows(q, page)
    }
}