package com.example.tvwatchseries.data.model

import com.google.gson.annotations.SerializedName

data class DetailedResponse(

	@field:SerializedName("tvShow")
	val tvShow: TvShow? = null
)

data class TvShow(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("countdown")
	val countdown: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("runtime")
	val runtime: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("pictures")
	val pictures: List<String>? = null,

	@field:SerializedName("network")
	val network: String? = null,

	@field:SerializedName("rating_count")
	val ratingCount: String? = null,

	@field:SerializedName("youtube_link")
	val youtubeLink: String? = null,

	@field:SerializedName("image_thumbnail_path")
	val imageThumbnailPath: String? = null,

	@field:SerializedName("image_path")
	val imagePath: String? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("description_source")
	val descriptionSource: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("permalink")
	val permalink: String? = null,

	@field:SerializedName("episodes")
	val episodes: List<EpisodesItem>? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class EpisodesItem(

	@field:SerializedName("air_date")
	val airDate: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("season")
	val season: Int? = null,

	@field:SerializedName("episode")
	val episode: Int? = null
)
