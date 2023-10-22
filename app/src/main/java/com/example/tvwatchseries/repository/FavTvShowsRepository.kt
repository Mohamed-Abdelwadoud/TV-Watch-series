package com.example.tvwatchseries.repository

import androidx.lifecycle.LiveData
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.data.model.TvShow
import com.example.tvwatchseries.data.source.local.TVShowDao

class FavTvShowsRepository(private val tvShowDao: TVShowDao) {

    var readFavTvShows: LiveData<List<TVShowEntity>> = tvShowDao.getAllTvShows()

    suspend fun addTask(f_TV: TVShowEntity) {
        tvShowDao.insertTvShow(f_TV)
    }

    suspend fun removeTV(id :String){
        tvShowDao.deleteByHeader(id)
    }
}