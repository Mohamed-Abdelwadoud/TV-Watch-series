package com.example.tvwatchseries.data.repository

import com.example.tvwatchseries.data.model.toDetailedTvShowModel
import com.example.tvwatchseries.data.source.remote.RemoteSource
import com.example.tvwatchseries.domain.model.DetailedTvShowModel
import com.example.tvwatchseries.domain.repository.ShowDetailsRepository
import com.example.tvwatchseries.utility.NetWorkResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShowDetailsRepositoryImp @Inject constructor(private val remoteSource: RemoteSource) :
    ShowDetailsRepository {

    override suspend fun getDetailedTVShows(id: String): Flow<NetWorkResponseResult<DetailedTvShowModel>> =
        flow {
            emit(NetWorkResponseResult.Loading(true))
            try {
                remoteSource.getTVShowDetails(id).collect { response ->
                    emit(NetWorkResponseResult.Success(response.toDetailedTvShowModel()))
                }

            } catch (e: HttpException) {
                emit(NetWorkResponseResult.Error("Network error: ${e.message()}"))
            } catch (e: IOException) {
                emit(NetWorkResponseResult.Error("IO error: ${e.message}"))
            } catch (e: Exception) {
                emit(NetWorkResponseResult.Error("Unknown error: ${e.message}"))
            }

//        val data: MutableLiveData<DetailedTvShowModel> = MutableLiveData<DetailedTvShowModel>()
//        remoteSource.getTVShowDetails(id)!!.enqueue(object : Callback<DetailedResponse?> {
//            override fun onResponse(
//                call: Call<DetailedResponse?>,
//                response: Response<DetailedResponse?>
//            ) {
//                data.value = response.body()?.toDetailedTvShowModel()
//            }
//
//            override fun onFailure(call: Call<DetailedResponse?>, t: Throwable) {
//                data.value = (null)
//            }
//        })
//
//        return data
        }

}