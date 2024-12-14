package com.example.tvwatchseries.data.repository

import androidx.lifecycle.LiveData
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.data.source.local.TVShowDao
import com.example.tvwatchseries.domain.repository.FavTvShowsRepository
import javax.inject.Inject

class FavTvShowsRepositoryImp @Inject constructor(private val tvShowDao: TVShowDao) : FavTvShowsRepository {

    override fun getFavTvShows(): LiveData<List<TVShowEntity>> {
        return tvShowDao.getAllTvShows()
    }

    override suspend fun addTvShow(show: TVShowEntity): Long {
        return tvShowDao.insertTvShow(show)
    }

    override suspend fun removeTvShow(id: Int): Int  {
      return tvShowDao.deleteTvShowById(id)
    }
}