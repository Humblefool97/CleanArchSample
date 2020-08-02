package com.tbs.presentation.mapper

import com.tbs.domain.model.Project
import com.tbs.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : Mapper<ProjectView, Project> {
    override fun mapToView(type: Project): ProjectView {
        return ProjectView(
            type.id, type.name, type.fullName,
            type.starCount, type.dateCreated, type.ownerName,
            type.ownerAvatar, type.isBookmarked
        )
    }
}