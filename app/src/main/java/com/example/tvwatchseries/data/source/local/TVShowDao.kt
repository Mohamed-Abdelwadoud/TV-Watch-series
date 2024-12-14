package com.example.tvwatchseries.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tvwatchseries.data.model.TVShowEntity

@Dao
interface TVShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TVShowEntity): Long

    @Query("select * from TVShowEntity ")
    fun getAllTvShows(): LiveData<List<TVShowEntity>>

    @Query("delete from TVShowEntity where showID =:id")
    suspend fun deleteTvShowById(id: Int) : Int

}