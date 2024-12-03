package com.example.tvwatchseries.domain.repository

import androidx.lifecycle.LiveData
import com.example.tvwatchseries.data.model.TVShowEntity

interface FavTvShowsRepository {
    fun getFavTvShows(): LiveData<List<TVShowEntity>>
    suspend fun addTvShow(show: TVShowEntity) : Long
    suspend fun removeTvShow(id :String)

}