package com.tbs.data.repository

import com.tbs.data.model.ProjectEntity
import io.reactivex.rxjava3.core.Observable

interface ProjectRemote {
    fun getProjects(): Observable<List<ProjectEntity>>
}