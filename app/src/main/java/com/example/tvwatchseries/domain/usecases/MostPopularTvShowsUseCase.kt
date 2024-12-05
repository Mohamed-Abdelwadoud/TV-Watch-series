package com.example.tvwatchseries.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.repository.MostPopularTvShowsRepository
import javax.inject.Inject

class MostPopularTvShowsUseCase@Inject constructor(private val mostPopularTvShowsRepository: MostPopularTvShowsRepository) {
    operator fun invoke(page: Int): MutableLiveData<MostPopularModel> =
        mostPopularTvShowsRepository.getMostPopularTVShows(page)
}