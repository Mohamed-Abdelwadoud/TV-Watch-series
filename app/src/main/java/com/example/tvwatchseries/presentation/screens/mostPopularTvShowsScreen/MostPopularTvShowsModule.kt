package com.example.tvwatchseries.presentation.screens.mostPopularTvShowsScreen


import com.example.tvwatchseries.domain.repository.MostPopularTvShowsRepository
import com.example.tvwatchseries.domain.usecases.MostPopularTvShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MostPopularTvShowsModule {

    @Provides
    @ViewModelScoped
    fun provideMostPopularTvShowsUseCase(mostPopularTvShowsRepository: MostPopularTvShowsRepository): MostPopularTvShowsUseCase {
        return MostPopularTvShowsUseCase(mostPopularTvShowsRepository)
    }
}