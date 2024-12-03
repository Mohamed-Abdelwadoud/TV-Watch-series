package com.example.tvwatchseries.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TVShowEntity(
    @PrimaryKey  var showID: String,
    var showName: String?,
    var showStartDate: String?,
    var showNetwork: String?,
    var showStatus: String?,
    var showImageBytearray:ByteArray
):java.io.Serializable {

}