package com.example.tvwatchseries.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.data.model.MostPopularResponse
import com.example.tvwatchseries.data.model.toMostPopularModel
import com.example.tvwatchseries.data.source.remote.RemoteSource
import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.repository.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(private val remoteSource: RemoteSource) : SearchRepository {

    override fun getSearchedShows(name: String, page: Int): MutableLiveData<MostPopularModel> {
        val data: MutableLiveData<MostPopularModel> = MutableLiveData<MostPopularModel>()
        remoteSource.searchTVShow(name,page)!!.enqueue(object : Callback<MostPopularResponse?> {
            override fun onResponse(
                call: Call<MostPopularResponse?>,
                response: Response<MostPopularResponse?>

            ) {
                data.value = response.body()?.toMostPopularModel()
            }
            override fun onFailure(call: Call<MostPopularResponse?>, t: Throwable) {
                data.value = (null)
            }
        })

        return data

    }

}