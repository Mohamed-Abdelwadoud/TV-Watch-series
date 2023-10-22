package com.example.tvwatchseries.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TVShowEntity(
    @PrimaryKey  var f_id: String,
    var f_name: String?,
    var f_startDate: String?,
    var f_network: String?,
    var f_status: String?,
    var f_imageBytearray:ByteArray
):java.io.Serializable {

}