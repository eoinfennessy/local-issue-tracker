package com.eoinfennessy.localissuetracker.ui.screens.createIssue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.local.IssueRepository
import com.eoinfennessy.localissuetracker.data.local.database.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateIssueViewModel @Inject constructor(
    private val issueRepository: IssueRepository
) : ViewModel() {
    fun addIssue(issue: Issue) {
        viewModelScope.launch {
            issueRepository.add(issue)
        }
    }
}