package com.example.tvwatchseries.data.model

import com.google.gson.annotations.SerializedName

data class MostPopularResponse(

	@field:SerializedName("tv_shows")
	val tvShows: List<TvShowsItem?>? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)

data class TvShowsItem(

	@field:SerializedName("end_date")
	val endDate: Any? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("image_thumbnail_path")
	val imageThumbnailPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("permalink")
	val permalink: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("network")
	val network: String? = null,

	@field:SerializedName("status")
	val status: String? = null
):java.io.Serializable{

}
