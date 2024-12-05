package com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.domain.usecases.FavTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavTvShowsViewModel @Inject constructor(private val favTvShowsUseCase: FavTvShowsUseCase ) : ViewModel() {

    val getFavTvShows: LiveData<List<TVShowEntity>> = favTvShowsUseCase.readFavTvShows

    fun addNewTvShow(tvShow: TVShowEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favTvShowsUseCase.addTvShow(tvShow)
        }
    }

    suspend fun removeTvShow(id: String) {
        viewModelScope.launch(Dispatchers.IO) { favTvShowsUseCase.removeTvShow(id)
        }
    }


}