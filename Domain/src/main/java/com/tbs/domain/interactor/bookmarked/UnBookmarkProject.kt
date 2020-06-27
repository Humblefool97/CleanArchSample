package com.tbs.domain.interactor.bookmarked

import com.tbs.domain.executer.PostExecutionThread
import com.tbs.domain.interactor.CompletableUseCase
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class UnBookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UnBookmarkProject.Params>(postExecutionThread) {

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null)
            throw  IllegalArgumentException("Null!")

        return projectsRepository.unBookmarkProject(params.projectId)
    }

}