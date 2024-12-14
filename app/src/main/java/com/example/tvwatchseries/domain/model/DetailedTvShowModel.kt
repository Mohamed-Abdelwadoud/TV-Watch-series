package com.example.tvwatchseries.domain.model



data class DetailedTvShowModel(
    val tvShow: TvShowModel? = null

)


data class TvShowModel(

    val endDate: String? = null,

    val country: String? = null,

    val rating: String? = null,

    val countdown: String? = null,

    val description: String? = null,

    val runtime: Int? = null,

    val url: String? = null,

    val pictures: List<String>? = null,

    val network: String? = null,

    val ratingCount: String? = null,

    val youtubeLink: String? = null,

    val imageThumbnailPath: String? = null,

    val imagePath: String? = null,

    val genres: List<String?>? = null,

    val descriptionSource: String? = null,

    val name: String? = null,

    val id: Int? = null,

    val permalink: String? = null,

    val episodes: List<EpisodesItemModel>? = emptyList(),

    val startDate: String? = null,

    val status: String? = null
)

data class EpisodesItemModel(

    val airDate: String? = null,

    val name: String? = null,

    val season: Int? = null,

    val episode: Int? = null
)


