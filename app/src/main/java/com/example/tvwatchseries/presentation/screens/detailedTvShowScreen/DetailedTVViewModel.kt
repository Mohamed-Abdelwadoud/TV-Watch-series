package com.example.tvwatchseries.presentation.screens.detailedTvShowScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.repository.ShowDetailsRepositoryImp
import com.example.tvwatchseries.data.source.remote.ApiClient
import com.example.tvwatchseries.domain.model.DetailedTvShowModel
import com.example.tvwatchseries.domain.usecases.ShowDetailsUseCase

class DetailedTVViewModel:ViewModel() {
    private val showDetailsUseCase = ShowDetailsUseCase(ShowDetailsRepositoryImp(ApiClient()))
//    private var showDetailsRepository: ShowDetailsRepositoryImp? = null
//
//    init {
//        showDetailsRepository = ShowDetailsRepositoryImp()
//    }

    fun getTVDetails(id:String) : MutableLiveData<DetailedTvShowModel> {
       return showDetailsUseCase.getDetails(id)
    }
}