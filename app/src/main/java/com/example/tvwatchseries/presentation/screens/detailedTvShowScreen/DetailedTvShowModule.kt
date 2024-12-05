package com.example.tvwatchseries.presentation.screens.detailedTvShowScreen

import com.example.tvwatchseries.domain.repository.ShowDetailsRepository
import com.example.tvwatchseries.domain.usecases.ShowDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DetailedTvShowModule {
    @Provides
    @ViewModelScoped
    fun provideShowDetailsUseCase(showDetailsRepository: ShowDetailsRepository): ShowDetailsUseCase {
        return ShowDetailsUseCase(showDetailsRepository)
    }

}