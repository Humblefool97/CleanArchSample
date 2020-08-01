package com.tbs.cache

import com.tbs.cache.db.ProjectsDatabase
import com.tbs.cache.mapper.CachedProjectMapper
import com.tbs.cache.model.Config
import com.tbs.data.model.ProjectEntity
import com.tbs.data.repository.ProjectCache
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val db: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectCache {
    override fun clearProjects(): Completable {
        return Completable.defer {
            db.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer {
            db.cachedProjectsDao().insertProjects(projects.map {
                mapper.mapToCached(it)
            })
            Completable.complete()
        }
    }

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return db.cachedProjectsDao().getProjects()
            .map {
                it.map { cachedProject ->
                    mapper.mapFromCached(cachedProject)
                }
            }
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return db.cachedProjectsDao().getBookmarkedProjects()
            .map {
                it.map { cachedProject ->
                    mapper.mapFromCached(cachedProject)
                }
            }
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return Completable.defer {
            db.cachedProjectsDao().setBookmarkStatus(true, projectId)
            Completable.complete()
        }
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return Completable.defer {
            db.cachedProjectsDao().setBookmarkStatus(false, projectId)
            Completable.complete()
        }
    }

    override fun areProjectsCached(): Single<Boolean> {
        return db.cachedProjectsDao().getProjects().isEmpty
            .map { isEmpty ->
                !isEmpty
            }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            db.configDao().insertConfig(Config(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1000).toLong()
        return db.configDao().getConfig()
            .single(Config(lastCacheTime = 0))
            .map {
                currentTime - it.lastCacheTime > expirationTime
            }
    }

}