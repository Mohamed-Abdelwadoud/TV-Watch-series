package com.example.tvwatchseries.presentation.screens.searchScreen

import com.example.tvwatchseries.domain.repository.SearchRepository
import com.example.tvwatchseries.domain.usecases.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {


    @Provides
    @ViewModelScoped
    fun provideSearchUseCase(searchRepository: SearchRepository): SearchUseCase {
        return SearchUseCase(searchRepository)
    }

}