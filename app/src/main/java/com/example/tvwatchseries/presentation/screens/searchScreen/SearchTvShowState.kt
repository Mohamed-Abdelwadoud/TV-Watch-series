package com.example.tvwatchseries.presentation.screens.searchScreen

import com.example.tvwatchseries.domain.model.MostPopularModel

data class SearchTvShowState(
    var isLoading: Boolean = false,
    var tvShows: MostPopularModel? = null,
    var error: String? = null
)
