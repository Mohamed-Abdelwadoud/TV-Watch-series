package com.example.tvwatchseries.domain.usecases

import androidx.lifecycle.LiveData
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.domain.repository.FavTvShowsRepository
import javax.inject.Inject

class FavTvShowsUseCase @Inject constructor(private val repository: FavTvShowsRepository) {
    suspend fun addTvShow(tvShow: TVShowEntity) {
        repository.addTvShow(tvShow)
    }

    suspend fun removeTvShow(id: String) :Int{
       return repository.removeTvShow(id)
    }

    val readFavTvShows: LiveData<List<TVShowEntity>> = repository.getFavTvShows()


}