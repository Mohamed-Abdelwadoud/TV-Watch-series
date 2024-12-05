package com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen

import com.example.tvwatchseries.domain.repository.FavTvShowsRepository
import com.example.tvwatchseries.domain.usecases.FavTvShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FavTvShowModule {
    @Provides
    @ViewModelScoped
    fun provideFavTvShowsUseCase(favTvShowsRepository: FavTvShowsRepository): FavTvShowsUseCase {
        return FavTvShowsUseCase(favTvShowsRepository)
    }
}