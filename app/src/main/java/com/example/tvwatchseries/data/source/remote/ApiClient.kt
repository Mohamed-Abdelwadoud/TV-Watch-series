package com.example.tvwatchseries.data.source.remote

import com.example.tvwatchseries.data.model.DetailedResponse
import com.example.tvwatchseries.data.model.MostPopularResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient() :
    RemoteSource {
        lateinit var apiService: ApiService
    //    companion object {
//        private var instance: Retrofit? = null
//        fun getRetrofitInstance(): Retrofit {
//            if (instance == null) {
//                instance = Retrofit.Builder()
//                    .baseUrl("https://www.episodate.com/api/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            return instance!!
//        }
//    }

    init {
       apiService =   Retrofit.Builder()
            .baseUrl("https://www.episodate.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

            .create(ApiService::class.java)

    }

    override fun getMostPopularTVShows(page: Int): Call<MostPopularResponse?>? {
        return apiService.getMostPopularTVShows(page)
    }

    override fun getTVShowDetails(tvShowId: String?): Call<DetailedResponse>? {
        return apiService.getTVShowDetails(tvShowId)
    }

    override fun searchTVShow(query: String?, page: Int): Call<MostPopularResponse?>? {
        return apiService.searchTVShow(query, page)
    }

}