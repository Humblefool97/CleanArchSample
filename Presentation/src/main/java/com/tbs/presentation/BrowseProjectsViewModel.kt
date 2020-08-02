package com.tbs.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tbs.domain.interactor.bookmarked.BookmarkProject
import com.tbs.domain.interactor.bookmarked.UnBookmarkProject
import com.tbs.domain.interactor.browse.GetProjects
import com.tbs.domain.model.Project
import com.tbs.presentation.mapper.Mapper
import com.tbs.presentation.mapper.ProjectViewMapper
import com.tbs.presentation.model.ProjectView
import com.tbs.presentation.sate.Resource
import com.tbs.presentation.sate.ResourceState
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
    private val getProjects: GetProjects?,
    private val bookmarkProject: BookmarkProject,
    private val unBookmarkProject: UnBookmarkProject,
    private val mapper: ProjectViewMapper
) : ViewModel() {
    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        getProjects?.dispose()
        super.onCleared()
    }

    init {
        fetchProjects()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    private fun fetchProjects(){
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        getProjects?.execute(ProjectsSubscriber(),null)
    }

    fun bookmarkProject(projectId:String){
        return bookmarkProject.execute(
            BookmarkProjectsSubscriber(),
            BookmarkProject.Param.forProject(projectId)
        )
    }

    fun unbookmarkProject(projectId: String) {
        return unBookmarkProject.execute(
            BookmarkProjectsSubscriber(),
        UnBookmarkProject.Params.forProject(projectId))
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Project>>() {
        override fun onComplete() {}

        override fun onNext(projectList: List<Project>?) {
            liveData.postValue(
                Resource(ResourceState.SUCCESS, projectList?.map {
                    mapper.mapToView(it)
                }, null)
            )
        }

        override fun onError(e: Throwable?) {
            liveData.postValue(
                Resource(ResourceState.ERROR, null, e?.message)
            )
        }
    }

    inner class BookmarkProjectsSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data, null))
        }

        override fun onError(e: Throwable?) {
            liveData.postValue(
                Resource(
                    ResourceState.ERROR, liveData.value?.data,
                    e?.localizedMessage
                )
            )
        }
    }

}