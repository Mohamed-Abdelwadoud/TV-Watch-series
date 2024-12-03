package com.example.tvwatchseries.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.data.repository.FavTvShowsRepositoryImp
import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.repository.MostPopularTvShowsRepository

class MostPopularTvShowsUseCase(private val mostPopularTvShowsRepository: MostPopularTvShowsRepository) {
    operator fun invoke(page: Int): MutableLiveData<MostPopularModel> =
        mostPopularTvShowsRepository.getMostPopularTVShows(page)
}