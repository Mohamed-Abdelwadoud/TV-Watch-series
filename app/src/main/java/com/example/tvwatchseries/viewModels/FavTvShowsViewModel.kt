package com.example.tvwatchseries.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.data.source.local.TVShowDatabase
import com.example.tvwatchseries.repository.FavTvShowsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavTvShowsViewModel(application: Application
) : AndroidViewModel(application) {
    val getFavTvShows: LiveData<List<TVShowEntity>>
    private val repository: FavTvShowsRepository

    init {
        val tvShowDao = TVShowDatabase.getTVShowDatabase(application.baseContext).getTVShowDao()
        repository = FavTvShowsRepository(tvShowDao)
        getFavTvShows = repository.readFavTvShows

    }

    fun addNewTvShow(f_tv: TVShowEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(f_tv)
        }
    }

    suspend fun removeTV(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeTV(id)
        }    }


}