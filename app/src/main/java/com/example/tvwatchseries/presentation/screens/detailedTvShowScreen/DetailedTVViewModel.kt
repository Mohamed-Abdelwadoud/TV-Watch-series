package com.example.tvwatchseries.presentation.screens.detailedTvShowScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.tvwatchseries.domain.usecases.ShowDetailsUseCase
import com.example.tvwatchseries.utility.NetWorkResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailedTVViewModel @Inject constructor(private val showDetailsUseCase: ShowDetailsUseCase) :
    ViewModel() {

    private val _detailedTvShowSate = MutableStateFlow(DetailedTvShowSate())
    val detailedTvShowSate get() = _detailedTvShowSate

    fun getTVDetails(id: String) {
        viewModelScope.launch {
            showDetailsUseCase.getDetails(id).onEach { result ->
                when (result) {
                    is NetWorkResponseResult.Success -> {
                        _detailedTvShowSate.value = DetailedTvShowSate(
                            detailedTvShowModel = result.data,
                            isLoading = false,
                            error = null
                        )

                    }

                    is NetWorkResponseResult.Error -> {
                        _detailedTvShowSate.value = DetailedTvShowSate(
                            error = result.message,
                            detailedTvShowModel = null,
                            isLoading = false
                        )
                    }

                    is NetWorkResponseResult.Loading -> {
                        _detailedTvShowSate.value = DetailedTvShowSate(
                            isLoading = true,
                            detailedTvShowModel = null,
                            error = null
                        )
                    }

                }
            }.launchIn(viewModelScope)
        }
    }


}