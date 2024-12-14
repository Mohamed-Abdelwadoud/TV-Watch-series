package com.example.tvwatchseries.domain.usecases

import com.example.tvwatchseries.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend fun getSearchedShows(q: String, page: Int) = searchRepository.getSearchedShows(q, page)

}