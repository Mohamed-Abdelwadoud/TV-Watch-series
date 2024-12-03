package com.example.tvwatchseries.domain.usecases

import com.example.tvwatchseries.domain.repository.ShowDetailsRepository

class ShowDetailsUseCase (private val showDetailsRepository: ShowDetailsRepository)  {
    fun getDetails(id: String) = showDetailsRepository.getDetailedTVShows(id)
}