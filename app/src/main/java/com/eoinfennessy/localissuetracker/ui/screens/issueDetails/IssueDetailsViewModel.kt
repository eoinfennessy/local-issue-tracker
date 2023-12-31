package com.eoinfennessy.localissuetracker.ui.screens.issueDetails

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.IssueDetails
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.service.DbService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueDetailsViewModel @Inject constructor(
    private val dbService: DbService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var issue = mutableStateOf(Issue())

    init {
        val taskId = savedStateHandle.get<String>(IssueDetails.idArg)
        if (taskId != null) {
            viewModelScope.launch {
                issue.value = dbService.getIssue(taskId) ?: Issue()
            }
        }
    }
}