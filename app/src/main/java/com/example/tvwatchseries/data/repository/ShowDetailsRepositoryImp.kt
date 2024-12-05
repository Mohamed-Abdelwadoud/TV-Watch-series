package com.example.tvwatchseries.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.toDetailedTvShowModel
import com.example.tvwatchseries.data.source.remote.RemoteSource
import com.example.tvwatchseries.domain.model.DetailedTvShowModel
import com.example.tvwatchseries.domain.repository.ShowDetailsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ShowDetailsRepositoryImp@Inject constructor(private val remoteSource: RemoteSource) : ShowDetailsRepository {

    override fun getDetailedTVShows(id: String): MutableLiveData<DetailedTvShowModel> {
        val data: MutableLiveData<DetailedTvShowModel> = MutableLiveData<DetailedTvShowModel>()
        remoteSource.getTVShowDetails(id)!!.enqueue(object : Callback<DetailedResponse?> {
            override fun onResponse(
                call: Call<DetailedResponse?>,
                response: Response<DetailedResponse?>
            ) {
                data.value = response.body()?.toDetailedTvShowModel()
            }

            override fun onFailure(call: Call<DetailedResponse?>, t: Throwable) {
                data.value = (null)
            }
        })

        return data
    }

}