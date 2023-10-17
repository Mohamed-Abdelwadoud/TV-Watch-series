package com.example.tvwatchseries.data.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 class ApiClient {

    companion object {
        private var instance: Retrofit? = null
        fun getRetrofitInstance(): Retrofit {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl("https://www.episodate.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance!!
        }
    }

}