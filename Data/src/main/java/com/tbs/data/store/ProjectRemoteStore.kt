package com.tbs.data.store

import com.tbs.data.model.ProjectEntity
import com.tbs.data.repository.ProjectRemote
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ProjectRemoteStore @Inject constructor(private val projectRemote: ProjectRemote) : ProjectDataStore {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectRemote.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn't supported here...")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clearing projects isn't supported here...")
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Getting bookmarked projects isn't supported here...")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }
}