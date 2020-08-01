package com.tbs.data.store

import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
        private val projectsCacheDataStore: ProjectsCacheDataStore,
        private val projectsRemoteDataStore: ProjectRemoteStore) {

    open fun getDataStore(projectsCached: Boolean,
                          cacheExpired: Boolean): ProjectDataStore {
        return if (projectsCached && !cacheExpired) {
            projectsCacheDataStore
        } else {
            projectsRemoteDataStore
        }
    }

    open fun getCacheDataStore(): ProjectDataStore {
        return projectsCacheDataStore
    }

    fun getRemoteDataStore(): ProjectRemoteStore {
        return projectsRemoteDataStore
    }
}