package com.tbs.domain.interactor.bookmarked

import com.tbs.domain.executer.PostExecutionThread
import com.tbs.domain.interactor.ObservableUseCase
import com.tbs.domain.model.Project
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetBookMarkedProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
):ObservableUseCase<List<Project>,Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getBookmarkedProjects()
    }
}