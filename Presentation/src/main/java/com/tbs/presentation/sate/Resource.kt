package com.tbs.presentation.sate

class Resource<out T> constructor(
    val state: ResourceState,
    val data: T?,
    val message: String?
) {
    fun <T> success(data: T): Resource<T> {
        return Resource(ResourceState.SUCCESS, data, null)
    }

    fun <T> error(message: String?): Resource<T> {
        return Resource(ResourceState.ERROR, null, message)
    }

    fun <T> loading(): Resource<T> {
        return Resource(ResourceState.LOADING, null, null)
    }
}