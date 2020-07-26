package com.tbs.domain.repository

import com.tbs.domain.model.Project
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface ProjectsRepository {
    fun getProjects(): Observable<List<Project>>

    fun bookmarkProject(projectId: String): Completable

    fun unBookmarkProject(projectId: String): Completable

    fun getBookmarkedProjects(): Observable<List<Project>>
}