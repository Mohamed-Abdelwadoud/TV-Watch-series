package com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.domain.usecases.FavTvShowsUseCase
import com.example.tvwatchseries.presentation.util.toTVShowEntity
import com.example.tvwatchseries.presentation.util.toTvShowsItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavTvShowsViewModel @Inject constructor(private val favTvShowsUseCase: FavTvShowsUseCase) :
    ViewModel() {

    val getFavTvShows: LiveData<List<TvShowsItemModel>> = favTvShowsUseCase.readFavTvShows.map {
        it.map { tvShowEntity->
            tvShowEntity.toTvShowsItemModel()
        }
    }

    fun addNewTvShow(tvShow: TvShowsItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            favTvShowsUseCase.addTvShow(tvShow.toTVShowEntity())
        }
    }

    suspend fun removeTvShow(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favTvShowsUseCase.removeTvShow(id)
        }
    }


}