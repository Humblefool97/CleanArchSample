package com.tbs.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tbs.cache.model.CachedProject
import com.tbs.cache.db.ProjectConstants.DELETE_PROJECTS
import com.tbs.cache.db.ProjectConstants.QUERY_BOOKMARKED_PROJECTS
import com.tbs.cache.db.ProjectConstants.QUERY_PROJECTS
import com.tbs.cache.db.ProjectConstants.QUERY_UPDATE_BOOKMARK_STATUS
import io.reactivex.rxjava3.core.Observable

@Dao
abstract class CachedProjectsDao {

    @Query(QUERY_PROJECTS)
    @JvmSuppressWildcards
    abstract fun getProjects(): Observable<List<CachedProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertProjects(projects: List<CachedProject>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_BOOKMARKED_PROJECTS)
    abstract fun getBookmarkedProjects(): Observable<List<CachedProject>>

    @Query(QUERY_UPDATE_BOOKMARK_STATUS)
    abstract fun setBookmarkStatus(
        isBookmarked: Boolean,
        projectId: String
    )
}