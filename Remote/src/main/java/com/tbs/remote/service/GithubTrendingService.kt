package com.tbs.remote.service

import com.tbs.remote.model.ProjectsResponseModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubTrendingService {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sortBy: String,
        @Query("order") order: String
    )
            : Observable<ProjectsResponseModel>
}