package com.tbs.domain.interactor.test

import com.tbs.domain.model.Project
import java.util.*

object ProjectDataFactory {
     fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    private fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    private fun makeProject(): Project {
        return Project(randomUuid(), randomUuid(), randomUuid(),
                randomUuid(), randomUuid(), randomUuid(),
                randomUuid(), randomBoolean())
    }

    fun makeProjectList(count: Int): List<Project> {
        val projectList = mutableListOf<Project>()
        repeat(count) {
            projectList.add(makeProject())
        }
        return projectList
    }

}