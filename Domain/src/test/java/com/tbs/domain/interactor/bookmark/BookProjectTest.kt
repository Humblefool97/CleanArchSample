package com.tbs.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.sun.jmx.mbeanserver.Repository
import com.tbs.domain.executer.PostExecutionThread
import com.tbs.domain.interactor.bookmarked.BookmarkProject
import com.tbs.domain.interactor.test.ProjectDataFactory
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class BookProjectTest {
    private lateinit var bookmarkProject: BookmarkProject

    @Mock
    lateinit var projectRepository: ProjectsRepository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete())

        val testObservable = bookmarkProject.buildUseCaseCompletable(
            BookmarkProject.Param.forProject(ProjectDataFactory.randomUuid())
        ).test()
        testObservable.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun validateShouldThrowException() {
        bookmarkProject.buildUseCaseCompletable().test()
    }

    fun stubBookmarkProject(completable: Completable) {
        whenever(projectRepository.bookmarkProject(any()))
            .thenReturn(completable)
    }
}