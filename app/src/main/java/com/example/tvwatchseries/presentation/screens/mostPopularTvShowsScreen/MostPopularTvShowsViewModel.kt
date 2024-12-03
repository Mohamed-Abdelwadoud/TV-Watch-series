package com.example.tvwatchseries.presentation.screens.mostPopularTvShowsScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.data.repository.MostPopularTvShowsRepositoryImp
import com.example.tvwatchseries.data.source.remote.ApiClient
import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.usecases.MostPopularTvShowsUseCase

class MostPopularTvShowsViewModel (): ViewModel() {
   private val  mostPopularTvShowsUseCase =MostPopularTvShowsUseCase(MostPopularTvShowsRepositoryImp(ApiClient()))

    fun getMostPopTvShows(page:Int): MutableLiveData<MostPopularModel> {
         return mostPopularTvShowsUseCase.invoke(page)
    }




}