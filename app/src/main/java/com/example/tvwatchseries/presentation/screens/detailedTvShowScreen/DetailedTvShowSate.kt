package com.example.tvwatchseries.presentation.screens.detailedTvShowScreen

import com.example.tvwatchseries.domain.model.DetailedTvShowModel


data class DetailedTvShowSate (
    var isLoading: Boolean = false,
    var detailedTvShowModel: DetailedTvShowModel? = null,
    var error: String? = null
)