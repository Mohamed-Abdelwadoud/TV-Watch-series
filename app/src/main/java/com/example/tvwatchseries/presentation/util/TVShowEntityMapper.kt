package com.example.tvwatchseries.presentation.util

import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.domain.model.TvShowsItemModel

fun TvShowsItemModel.toTVShowEntity(): TVShowEntity {
    return TVShowEntity(
        showID = id,
        showEndDate = endDate?:"No Found",
        showCountry = country?:"No Found",
        imageThumbnailPath = imageThumbnailPath?:"No Found",
        showName = name?:"No Found",
        showPermalink = permalink?:"No Found",
        showStartDate = startDate?:"No Found",
        showNetwork = network?:"No Found",
        showStatus = status?:"No Found"
    )
}

fun TVShowEntity.toTvShowsItemModel() : TvShowsItemModel{
    return TvShowsItemModel(
        endDate = showEndDate,
        country = showCountry,
        imageThumbnailPath = imageThumbnailPath,
        name = showName,
        id = showID,
        permalink = showPermalink,
        startDate = showStartDate,
        network = showNetwork,
        status = showStatus

        )

}