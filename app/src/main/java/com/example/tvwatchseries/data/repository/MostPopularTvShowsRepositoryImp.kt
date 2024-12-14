package com.example.tvwatchseries.data.repository

import com.example.tvwatchseries.data.model.toMostPopularModel
import com.example.tvwatchseries.data.source.remote.RemoteSource
import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.repository.MostPopularTvShowsRepository
import com.example.tvwatchseries.utility.NetWorkResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MostPopularTvShowsRepositoryImp @Inject constructor(private val remoteSource: RemoteSource) :
    MostPopularTvShowsRepository {


    override suspend fun getMostPopularTVShows(page: Int): Flow<NetWorkResponseResult<MostPopularModel>> =
        flow {
            emit(NetWorkResponseResult.Loading(true))

            try {
                remoteSource.getMostPopularTVShows(page).collect { response ->
                    emit(NetWorkResponseResult.Success(response.toMostPopularModel())) // Emit Success
                }
                // old flow
//                remoteSource.getMostPopularTVShows(page).map {
//                    NetWorkResponseResult.Success(it.toMostPopularModel())


            } catch (e: HttpException) {
                emit(NetWorkResponseResult.Error("Network error: ${e.message()}"))
            } catch (e: IOException) {
                emit(NetWorkResponseResult.Error("IO error: ${e.message}"))
            } catch (e: Exception) {
                emit(NetWorkResponseResult.Error("Unknown error: ${e.message}"))
            }

//        val data: MutableLiveData<MostPopularModel> = MutableLiveData<MostPopularModel>()
//
//        remoteSource.getMostPopularTVShows(page)!!.enqueue(object : Callback<MostPopularResponse?> {
//            override fun onResponse(
//                call: Call<MostPopularResponse?>,
//                response: Response<MostPopularResponse?>
//
//            ) {
//                data.value = response.body()?.toMostPopularModel()
//            }
//
//            override fun onFailure(call: Call<MostPopularResponse?>, t: Throwable) {
//                data.value = (null)
//            }
//        })
//
//        return data
        }


}