package com.tbs.domain.interactor.browse

import com.tbs.domain.executer.PostExecutionThread
import com.tbs.domain.interactor.ObservableUseCase
import com.tbs.domain.model.Project
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Project>, Nothing?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getProjects()
    }
}