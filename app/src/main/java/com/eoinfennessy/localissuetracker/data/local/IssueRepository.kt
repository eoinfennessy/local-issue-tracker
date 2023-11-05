package com.eoinfennessy.localissuetracker.data.local

import com.eoinfennessy.localissuetracker.data.local.database.Issue
import com.eoinfennessy.localissuetracker.data.local.database.IssueDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IssueRepository {
    val issues: Flow<List<Issue>>

    fun getIssue(id: Int): Flow<Issue>

    suspend fun add(issue: Issue)
    suspend fun delete(issue: Issue)
}

class DefaultIssueRepository @Inject constructor(
    private val issueDao: IssueDao
) : IssueRepository {

    override val issues: Flow<List<Issue>> = issueDao.getIssues()

    override fun getIssue(id: Int): Flow<Issue> {
        return issueDao.getIssue(id)
    }

    override suspend fun add(issue: Issue) {
        issueDao.insertIssue(issue)
    }

    override suspend fun delete(issue: Issue) {
        issueDao.deleteIssue(issue)
    }
}