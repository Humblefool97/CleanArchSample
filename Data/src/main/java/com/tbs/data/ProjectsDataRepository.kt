package com.tbs.data

import com.tbs.data.mapper.ProjectEntityMapper
import com.tbs.data.repository.ProjectCache
import com.tbs.data.store.ProjectsDataStoreFactory
import com.tbs.domain.model.Project
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject

/**
 *   projectMapper : To map data layer representation to domain layer representation
 *   projectDataStoreFactory: To access the source
 *   projectCache : To get details about cache
 */
class ProjectsDataRepository @Inject constructor(
        private val projectMapper: ProjectEntityMapper,
        private val factory: ProjectsDataStoreFactory,
        private val cache: ProjectCache
) : ProjectsRepository {
    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(cache.areProjectsCached().toObservable(),
                cache.isProjectsCacheExpired().toObservable(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areCached, isExpired ->
                    Pair(areCached, isExpired)
                })
                .flatMap {
                    factory.getDataStore(it.first, it.second).getProjects()
                }.flatMap {
                    factory.getCacheDataStore()
                            .saveProjects(it)
                            .andThen(Observable.just(it))
                }.map { projects ->
                    projects.map {
                        projectMapper.mapFromEntity(it)
                    }
                }
    }

    override fun bookmarkProject(projectId: String): Completable {
        return factory.getCacheDataStore().setProjectAsBookmarked(projectId)
    }

    override fun unBookmarkProject(projectId: String): Completable {
        return factory.getCacheDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return factory.getCacheDataStore().getBookmarkedProjects()
                .map { projects ->
                    projects.map {
                        projectMapper.mapFromEntity(it)
                    }

                }
    }
}