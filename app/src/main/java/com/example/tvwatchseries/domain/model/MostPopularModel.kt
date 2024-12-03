package com.example.tvwatchseries.domain.model


data class MostPopularModel (

    val tvShows: List<TvShowsItemModel?>? = null,

    val total: Int? = null,

    val pages: Int? = null,

    val page: Int? = null
)

data class TvShowsItemModel(

    val endDate: Any? = null,

    val country: String? = null,

    val imageThumbnailPath: String? = null,

    val name: String? = null,

    val id: Int? = null,

    val permalink: String? = null,

    val startDate: String? = null,

    val network: String? = null,

    val status: String? = null
):java.io.Serializable{

}
