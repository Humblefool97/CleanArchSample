package com.tbs.remote.mapper

import com.tbs.data.model.ProjectEntity
import com.tbs.data.repository.ProjectRemote
import com.tbs.remote.service.GithubTrendingService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(private val service: GithubTrendingService,
private val mapper: ProjectsResponseModelMapper): ProjectRemote {

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return service.searchRepositories("language:kotlin", "stars", "desc")
            .map { projectsResponseModel ->
                projectsResponseModel.items.map { projectModel ->
                    mapper.mapFromModel(projectModel)
                }
            }
    }
}