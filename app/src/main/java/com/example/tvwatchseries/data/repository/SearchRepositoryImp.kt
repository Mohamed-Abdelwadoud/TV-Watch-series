package com.example.tvwatchseries.data.repository

import com.example.tvwatchseries.data.model.toMostPopularModel
import com.example.tvwatchseries.data.source.remote.RemoteSource
import com.example.tvwatchseries.domain.model.MostPopularModel
import com.example.tvwatchseries.domain.repository.SearchRepository
import com.example.tvwatchseries.utility.NetWorkResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(private val remoteSource: RemoteSource) :
    SearchRepository {

    override suspend fun getSearchedShows(name: String, page: Int): Flow<NetWorkResponseResult<MostPopularModel>>  = flow {
        emit(NetWorkResponseResult.Loading(true))
         try {
            remoteSource.searchTVShow(name, page).collect { response ->
                emit(NetWorkResponseResult.Success(response.toMostPopularModel()))
            }

        } catch (e: HttpException) {
             emit(NetWorkResponseResult.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
             emit(NetWorkResponseResult.Error("IO error: ${e.message}"))
        } catch (e: Exception) {
             emit(NetWorkResponseResult.Error("Unknown error: ${e.message}"))
        }

//        val data: MutableLiveData<MostPopularModel> = MutableLiveData<MostPopularModel>()
//        remoteSource.searchTVShow(name,page)!!.enqueue(object : Callback<MostPopularResponse?> {
//            override fun onResponse(
//                call: Call<MostPopularResponse?>,
//                response: Response<MostPopularResponse?>
//
//            ) {
//                data.value = response.body()?.toMostPopularModel()
//            }
//            override fun onFailure(call: Call<MostPopularResponse?>, t: Throwable) {
//                data.value = (null)
//            }
//        })
//
//        return data

    }

}