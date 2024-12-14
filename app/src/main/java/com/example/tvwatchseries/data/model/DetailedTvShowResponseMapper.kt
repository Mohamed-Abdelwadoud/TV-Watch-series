package com.example.tvwatchseries.data.model

import com.example.tvwatchseries.domain.model.DetailedTvShowModel
import com.example.tvwatchseries.domain.model.EpisodesItemModel
import com.example.tvwatchseries.domain.model.TvShowModel

fun DetailedResponse.toDetailedTvShowModel(): DetailedTvShowModel =
    DetailedTvShowModel(
        tvShow = tvShow?.toTvShowModel()
    )

fun TvShow.toTvShowModel(): TvShowModel =
    TvShowModel(
        endDate = endDate,
        country = country,
        rating = rating,
        countdown = countdown,
        description = description,
        runtime = runtime,
        url = url,
        pictures = pictures,
        network = network,
        ratingCount = ratingCount,
        youtubeLink = youtubeLink,
        imageThumbnailPath = imageThumbnailPath,
        imagePath = imagePath,
        genres = genres,
        descriptionSource = descriptionSource,
        name = name,
        id = id,
        permalink = permalink,
        episodes = episodes?.map { it.toEpisodesItemModel() },
        startDate = startDate,
    )

fun EpisodesItem.toEpisodesItemModel(): EpisodesItemModel =
    EpisodesItemModel(
        airDate = airDate,
        name = name,
        season = season,
        episode = episode
    )
