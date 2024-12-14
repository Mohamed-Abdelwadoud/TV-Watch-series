package com.example.tvwatchseries.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TVShowEntity(
    @PrimaryKey
    var showID: Int,

    val showEndDate: String ,

    val showCountry: String,

    val imageThumbnailPath: String,

    val showName: String,


    val showPermalink: String,

    val showStartDate: String,

    val showNetwork: String,

    val showStatus: String


) : java.io.Serializable {

}