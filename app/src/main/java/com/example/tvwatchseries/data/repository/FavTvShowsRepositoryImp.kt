package com.example.tvwatchseries.data.repository

import androidx.lifecycle.LiveData
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.data.source.local.TVShowDao
import com.example.tvwatchseries.domain.repository.FavTvShowsRepository

class FavTvShowsRepositoryImp(private val tvShowDao: TVShowDao) : FavTvShowsRepository {

    //    var readFavTvShows: LiveData<List<TVShowEntity>> = tvShowDao.getAllTvShows()
//
//    suspend fun addTask(f_TV: TVShowEntity) {
//        tvShowDao.insertTvShow(f_TV)
//    }
//
//    suspend fun removeTV(id :String){
//        tvShowDao.deleteByHeader(id)
//    }
    override fun getFavTvShows(): LiveData<List<TVShowEntity>> {
       return tvShowDao.getAllTvShows()
    }

    override suspend fun addTvShow(tvShow: TVShowEntity): Long {
        return tvShowDao.insertTvShow(tvShow)
    }

    override suspend fun removeTvShow(id: String) {
        return tvShowDao.deleteTvShowById(id)

    }
}