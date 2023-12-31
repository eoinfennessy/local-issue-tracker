package com.eoinfennessy.localissuetracker.data.service

import com.eoinfennessy.localissuetracker.data.model.Issue
import kotlinx.coroutines.flow.Flow

interface DbService {
    val issues: Flow<List<Issue>>
    val userIssues: Flow<List<Issue>>

    suspend fun getIssue(issueId: String): Issue?
    suspend fun save(issue: Issue): String
    suspend fun update(issue: Issue)
    suspend fun delete(issueId: String)
}