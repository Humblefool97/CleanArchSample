package com.tbs.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.whenever
import com.tbs.domain.executer.PostExecutionThread
import com.tbs.domain.interactor.bookmarked.GetBookMarkedProjects
import com.tbs.domain.interactor.test.ProjectDataFactory
import com.tbs.domain.model.Project
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.Exception

class GetBookmarkProjectsTest {
    private lateinit var getBookmarkProjects: GetBookMarkedProjects

    @Mock
    lateinit var projectRepository: ProjectsRepository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getBookmarkProjects = GetBookMarkedProjects(projectRepository, postExecutionThread)
    }

    @Test
    fun testGetBookmarkProjectsCompletable() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))

        val testObservable = getBookmarkProjects.buildUseCaseObservable().test()
        testObservable.assertComplete()
    }

    @Test
    fun testValidateData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))

        val testObservable = getBookmarkProjects.buildUseCaseObservable().test()
        testObservable.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectRepository.getBookmarkedProjects())
            .thenReturn(observable)
    }
}