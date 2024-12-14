package com.example.tvwatchseries.domain.usecases

import com.example.tvwatchseries.domain.repository.MostPopularTvShowsRepository
import javax.inject.Inject

class MostPopularTvShowsUseCase@Inject constructor(private val mostPopularTvShowsRepository: MostPopularTvShowsRepository) {
  suspend operator fun invoke(page: Int) =
        mostPopularTvShowsRepository.getMostPopularTVShows(page)
}