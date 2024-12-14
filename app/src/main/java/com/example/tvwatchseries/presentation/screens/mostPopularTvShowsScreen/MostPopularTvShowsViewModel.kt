package com.example.tvwatchseries.presentation.screens.mostPopularTvShowsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvwatchseries.domain.usecases.MostPopularTvShowsUseCase
import com.example.tvwatchseries.utility.NetWorkResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostPopularTvShowsViewModel @Inject constructor(private val mostPopularTvShowsUseCase: MostPopularTvShowsUseCase) :
    ViewModel() {
    private val _mostPopularTvShows = MutableSharedFlow<MostPopularTvShowsState>(replay = 1)
    val mostPopularTvShows: SharedFlow<MostPopularTvShowsState> get() = _mostPopularTvShows

    fun getMostPopTvShows(page: Int) {
        viewModelScope.launch {
            mostPopularTvShowsUseCase.invoke(page = page).onEach { result ->
                when (result) {
                    is NetWorkResponseResult.Success -> {
                        if (result.data.tvShows.isNotEmpty()) {
                            _mostPopularTvShows.emit(
                                MostPopularTvShowsState(tvShows = result.data)
                            )
                        } else {
                            _mostPopularTvShows.emit(
                                MostPopularTvShowsState(
                                    error = "NO Data Found",
                                    tvShows = null,
                                    isLoading = false
                                )
                            )

                        }
                    }

                    is NetWorkResponseResult.Error -> {
                        _mostPopularTvShows.emit(
                            MostPopularTvShowsState(
                                error = result.message,
                                tvShows = null,
                                isLoading = false
                            )
                        )

                    }

                    is NetWorkResponseResult.Loading -> {
                        _mostPopularTvShows.emit(
                            MostPopularTvShowsState(isLoading = true, tvShows = null, error = null)
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


}