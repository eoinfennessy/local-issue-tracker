package com.eoinfennessy.localissuetracker.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.eoinfennessy.localissuetracker.data.local.DefaultIssueRepository
import com.eoinfennessy.localissuetracker.data.local.IssueRepository
import com.eoinfennessy.localissuetracker.data.local.database.Issue
import com.eoinfennessy.localissuetracker.data.local.database.IssueStatus
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsIssueRepository(
        issueRepository: DefaultIssueRepository
    ): IssueRepository
}

class FakeIssueRepository @Inject constructor() : IssueRepository {
    override val issues: Flow<List<Issue>> = flowOf(fakeIssues)

    override suspend fun add(issue: Issue) {
        throw NotImplementedError()
    }

    override suspend fun delete(issue: Issue) {
        throw NotImplementedError()
    }
}

val fakeIssues = listOf(
    Issue("Issue 0", "Description 0", IssueStatus.OPEN, 0),
    Issue("Issue 1", "Description 1", IssueStatus.OPEN, 1),
    Issue("Issue 2", "Description 2", IssueStatus.OPEN, 2),
    Issue("Issue 3", "Description 3", IssueStatus.OPEN, 3),
)