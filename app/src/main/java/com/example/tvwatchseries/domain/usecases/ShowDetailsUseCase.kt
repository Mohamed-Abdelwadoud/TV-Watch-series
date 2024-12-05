package com.example.tvwatchseries.domain.usecases

import com.example.tvwatchseries.domain.repository.ShowDetailsRepository
import javax.inject.Inject

class ShowDetailsUseCase @Inject constructor (private val showDetailsRepository: ShowDetailsRepository)  {
    fun getDetails(id: String) = showDetailsRepository.getDetailedTVShows(id)
}