package com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.data.source.local.TVShowDatabase
import com.example.tvwatchseries.data.repository.FavTvShowsRepositoryImp
import com.example.tvwatchseries.domain.usecases.FavTvShowsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavTvShowsViewModel(
    application: Application
) : AndroidViewModel(application) {
    val getFavTvShows: LiveData<List<TVShowEntity>>
//    private val repository: FavTvShowsRepositoryImp

    private val favTvShowsUseCase = FavTvShowsUseCase(
        FavTvShowsRepositoryImp(
            TVShowDatabase.getTVShowDatabase(application.baseContext).getTVShowDao()
        )
    )

    init {
//        val tvShowDao =
//        repository = FavTvShowsRepositoryImp(tvShowDao)
            getFavTvShows = favTvShowsUseCase.readFavTvShows

    }

    fun addNewTvShow(tvShow: TVShowEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favTvShowsUseCase.addTvShow(tvShow)
        }
    }

    suspend fun removeTvShow(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favTvShowsUseCase.removeTvShow(id)
        }
    }


}