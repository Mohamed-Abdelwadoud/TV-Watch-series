package com.example.tvwatchseries.domain.usecases

import com.example.tvwatchseries.domain.repository.SearchRepository

class SearchUseCase ( private val searchRepository: SearchRepository){
    fun getSearchedShows(q:String,page: Int) = searchRepository.getSearchedShows(q,page)

}