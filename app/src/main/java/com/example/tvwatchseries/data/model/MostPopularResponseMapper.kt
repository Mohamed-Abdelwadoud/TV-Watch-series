package com.example.tvwatchseries.data.model

import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.model.TvShowsItemModel

fun MostPopularResponse.toMostPopularModel(): MostPopularModel =
    MostPopularModel(
        tvShows = tvShows?.map { it?.toTvShowsItemModel() },
        total = total,
        pages = pages,
        page = page


    )


fun TvShowsItem.toTvShowsItemModel(): TvShowsItemModel =
    TvShowsItemModel(
        endDate = endDate,
        country = country,
        imageThumbnailPath = imageThumbnailPath,
        name = name,
        id = id,
        permalink = permalink,
        startDate = startDate,
        network = network,
        status = status


    )