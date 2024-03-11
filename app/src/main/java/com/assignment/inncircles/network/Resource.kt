package com.assignment.inncircles.network

sealed class Resource<out T> {

    object Loading : Resource<Nothing>()

    data class Success<T>(val data: T) : Resource<T>()

    data class Error(val error: Throwable, val errorCode: Int? = null, val errorBody: String? = null) :
        Resource<Nothing>()
}