package com.example.tvwatchseries.presentation.screens.mostPopularTvShowsScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.usecases.MostPopularTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MostPopularTvShowsViewModel @Inject constructor(private val mostPopularTvShowsUseCase: MostPopularTvShowsUseCase) :
    ViewModel() {

    fun getMostPopTvShows(page: Int): MutableLiveData<MostPopularModel> {
        return mostPopularTvShowsUseCase.invoke(page)
    }


}