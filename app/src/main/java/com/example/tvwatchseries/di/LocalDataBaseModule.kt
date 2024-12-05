package com.example.tvwatchseries.di

import android.content.Context
import androidx.room.Room
import com.example.tvwatchseries.data.repository.FavTvShowsRepositoryImp
import com.example.tvwatchseries.data.source.local.TVShowDao
import com.example.tvwatchseries.data.source.local.TVShowDatabase
import com.example.tvwatchseries.domain.repository.FavTvShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataBaseModule {

    @Provides
    @Singleton
    fun provideFavTvShowsRepository(tvShowDao: TVShowDao): FavTvShowsRepository {
        return FavTvShowsRepositoryImp(tvShowDao)
    }

    @Provides
    @Singleton
    fun provideTVShowDao(tvShowDatabase: TVShowDatabase): TVShowDao {
        return tvShowDatabase.getTVShowDao()
    }


    @Provides
    @Singleton
    fun provideTVShowDatabase(@ApplicationContext context: Context): TVShowDatabase {
        return Room.databaseBuilder(
            context,
            TVShowDatabase::class.java,
            "tv_show_database"
        ).build()
    }
}