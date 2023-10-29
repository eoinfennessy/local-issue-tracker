package com.eoinfennessy.localissuetracker.ui.screens.issues

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.eoinfennessy.localissuetracker.data.Issue
import com.eoinfennessy.localissuetracker.data.IssueStatus

class IssuesViewModel : ViewModel() {
    private val _issues = getPlaceholderIssues().toMutableStateList()
    val issues: List<Issue>
        get() = _issues

    fun remove(issue: Issue) {
        _issues.remove(issue)
    }
}

private fun getPlaceholderIssues() = List(30) {
        i -> Issue(name = "Name $i", description = "Description $i", status = IssueStatus.CLOSED)
}
