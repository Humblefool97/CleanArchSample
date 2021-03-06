package com.tbs.domain.interactor

import com.nhaarman.mockito_kotlin.whenever
import com.tbs.domain.executer.PostExecutionThread
import com.tbs.domain.interactor.browse.GetProjects
import com.tbs.domain.interactor.test.ProjectDataFactory
import com.tbs.domain.model.Project
import com.tbs.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest {
    private lateinit var getProjects: GetProjects

    @Mock
    lateinit var projectsRepository: ProjectsRepository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getProjectsCompletes() {
        val projectObservable = Observable.just(ProjectDataFactory.makeProjectList(2))
        stubGetProjects(projectObservable)

        val testObservable = getProjects.buildUseCaseObservable().test()
        testObservable.assertComplete()
    }

    @Test
    fun getProjectsValidateData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))

        val testObservable = getProjects.buildUseCaseObservable().test()
        testObservable.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getProjects())
                .thenReturn(observable)
    }
}