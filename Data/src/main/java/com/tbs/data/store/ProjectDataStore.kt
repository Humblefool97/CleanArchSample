package com.tbs.data.store

import com.tbs.data.model.ProjectEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

/**
 * Interfaces that enforces the methods that needs to be implemented
 * while accessing the cache and remote layer.
 *
 * Similar to Analytics manager interface if you multiple analytics products
 */
interface ProjectDataStore {
    fun getProjects(): Observable<List<ProjectEntity>>

    fun saveProjects(projects: List<ProjectEntity>): Completable

    fun clearProjects(): Completable

    fun getBookmarkedProjects(): Observable<List<ProjectEntity>>

    fun setProjectAsBookmarked(projectId: String): Completable

    fun setProjectAsNotBookmarked(projectId: String): Completable
}