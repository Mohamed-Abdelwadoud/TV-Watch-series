package com.example.tvwatchseries.presentation.screens.mostPopularTvShowsScreen

import com.example.tvwatchseries.domain.model.MostPopularModel

data class MostPopularTvShowsState(
    var isLoading: Boolean = false,
    var tvShows: MostPopularModel? = null ,
    var error: String? = null
)
