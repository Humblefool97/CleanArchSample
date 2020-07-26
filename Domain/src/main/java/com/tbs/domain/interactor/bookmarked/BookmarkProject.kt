package com.tbs.domain.interactor.bookmarked

import com.tbs.domain.executer.PostExecutionThread
import com.tbs.domain.interactor.CompletableUseCase
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class BookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<BookmarkProject.Param>(postExecutionThread) {

    data class Param constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Param {
                return Param(projectId)
            }
        }
    }

    public override fun buildUseCaseCompletable(params: Param?): Completable {
        if (params == null)
            throw  IllegalArgumentException("Must supply arguments!")
        return projectsRepository.bookmarkProject(params.projectId)
    }
}