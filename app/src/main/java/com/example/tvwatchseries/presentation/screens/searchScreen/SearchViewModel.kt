package com.example.tvwatchseries.presentation.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvwatchseries.domain.usecases.SearchUseCase
import com.example.tvwatchseries.utility.NetWorkResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val _mostPopularTvShows = MutableStateFlow(SearchTvShowState())
    val mostPopularTvShows get() = _mostPopularTvShows

    fun getSearchTVShows(q: String, page: Int) {
        viewModelScope.launch {
            searchUseCase.getSearchedShows(q, page).onEach { result ->
                when (result) {
                    is NetWorkResponseResult.Success -> {
                        _mostPopularTvShows.value = SearchTvShowState(tvShows = result.data)

                    }

                    is NetWorkResponseResult.Error -> {
                        _mostPopularTvShows.value = SearchTvShowState(
                            error = result.message,
                            tvShows = null,
                            isLoading = false
                        )

                    }

                    is NetWorkResponseResult.Loading -> {
                        _mostPopularTvShows.value =
                            SearchTvShowState(isLoading = true, tvShows = null, error = null)

                    }
                }
            }.launchIn(viewModelScope)
        }


    }
}
