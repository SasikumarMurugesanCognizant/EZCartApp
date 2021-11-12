package com.cts.ezcartapp.domain.model

sealed class DataHolder<out T : Any> {

    data class Success<out T : Any>(val data: T) : DataHolder<T>()

    data class Fail(val error: Throwable? = null) : DataHolder<Nothing>()

    data class Loading(val isLoading:Boolean=false) : DataHolder<Nothing>()

}
