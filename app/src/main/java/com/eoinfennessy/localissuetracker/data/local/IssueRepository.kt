package com.eoinfennessy.localissuetracker.data.local

import com.eoinfennessy.localissuetracker.data.local.database.Issue
import com.eoinfennessy.localissuetracker.data.local.database.IssueDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IssueRepository {
    val issues: Flow<List<Issue>>

    suspend fun add(issue: Issue)
}

class DefaultIssueRepository @Inject constructor(
    private val issueDao: IssueDao
) : IssueRepository {

    override val issues: Flow<List<Issue>> = issueDao.getIssues()

    override suspend fun add(issue: Issue) {
        issueDao.insertIssue(issue)
    }
}