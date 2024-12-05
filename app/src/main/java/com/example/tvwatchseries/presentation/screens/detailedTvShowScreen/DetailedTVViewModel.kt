package com.example.tvwatchseries.presentation.screens.detailedTvShowScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.tvwatchseries.domain.model.DetailedTvShowModel
import com.example.tvwatchseries.domain.usecases.ShowDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailedTVViewModel @Inject constructor(private val showDetailsUseCase: ShowDetailsUseCase) :
    ViewModel() {

    fun getTVDetails(id: String): MutableLiveData<DetailedTvShowModel> {
        return showDetailsUseCase.getDetails(id)
    }
}