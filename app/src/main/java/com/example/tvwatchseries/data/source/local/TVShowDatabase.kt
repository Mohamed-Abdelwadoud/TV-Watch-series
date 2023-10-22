package com.example.tvwatchseries.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvwatchseries.data.model.TVShowEntity

@Database(entities = [TVShowEntity::class], version = 1, exportSchema = false)
abstract class TVShowDatabase : RoomDatabase() {
    abstract fun getTVShowDao(): TVShowDao

    companion object {
        private var instance: TVShowDatabase? = null
        fun getTVShowDatabase(context: Context): TVShowDatabase {
            val tempInstance = instance
            if (tempInstance == null) {
                synchronized(this) {
                    val instant = Room.databaseBuilder(
                        context,
                        TVShowDatabase::class.java,
                        name = "TvShows_ROOM_DB"
                    ).build()
                    instance = instant
                    return instant
                }

            } else return tempInstance

        }

    }
}