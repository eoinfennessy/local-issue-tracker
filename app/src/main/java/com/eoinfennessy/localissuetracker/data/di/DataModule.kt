package com.eoinfennessy.localissuetracker.data.di

import android.net.Uri
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
import java.util.Date
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

val fakeIssues = List(30) { i ->
    Issue(
        "Issue $i",
        "Description $i",
        IssueStatus.OPEN,
        i.toDouble(),
        i.toDouble(),
        Date(),
        Uri.EMPTY
    )
}