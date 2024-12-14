package com.example.tvwatchseries.utility



sealed class NetWorkResponseResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetWorkResponseResult<T>()
    data class Error(val message: String) : NetWorkResponseResult<Nothing>()
    data class Loading(val isLoading: Boolean) : NetWorkResponseResult<Nothing>()
}