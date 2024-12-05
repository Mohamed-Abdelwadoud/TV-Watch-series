package com.example.tvwatchseries.di

import com.example.tvwatchseries.data.repository.MostPopularTvShowsRepositoryImp
import com.example.tvwatchseries.data.repository.SearchRepositoryImp
import com.example.tvwatchseries.data.repository.ShowDetailsRepositoryImp
import com.example.tvwatchseries.data.source.remote.ApiClient
import com.example.tvwatchseries.data.source.remote.ApiService
import com.example.tvwatchseries.domain.repository.MostPopularTvShowsRepository
import com.example.tvwatchseries.domain.repository.SearchRepository
import com.example.tvwatchseries.domain.repository.ShowDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {


    @Provides
    @Singleton
    fun provideMostPopularTvShowsRepository(apiClient: ApiClient): MostPopularTvShowsRepository {
        return MostPopularTvShowsRepositoryImp(apiClient)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(apiClient: ApiClient): SearchRepository {
        return SearchRepositoryImp(apiClient)
    }

    @Provides
    @Singleton
    fun provideShowDetailsRepository(apiClient: ApiClient): ShowDetailsRepository {
        return ShowDetailsRepositoryImp(apiClient)
    }

    @Provides
    @Singleton
    fun provideApiClient(apiService: ApiService): ApiClient {
        return ApiClient(apiService)
    }


}