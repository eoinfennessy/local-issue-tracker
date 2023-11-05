package com.eoinfennessy.localissuetracker.ui.screens.issueDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.IssueDetails
import com.eoinfennessy.localissuetracker.data.local.IssueRepository
import com.eoinfennessy.localissuetracker.data.local.database.Issue
import com.eoinfennessy.localissuetracker.ui.screens.issueDetails.IssueDetailsUiState.Error
import com.eoinfennessy.localissuetracker.ui.screens.issueDetails.IssueDetailsUiState.Loading
import com.eoinfennessy.localissuetracker.ui.screens.issueDetails.IssueDetailsUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class IssueDetailsViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Int = savedStateHandle[IssueDetails.idArg]!!

    val uiState: StateFlow<IssueDetailsUiState> = issueRepository
        .getIssue(taskId).map<Issue, IssueDetailsUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
}

sealed interface IssueDetailsUiState {
    object Loading : IssueDetailsUiState
    data class Error(val throwable: Throwable) : IssueDetailsUiState
    data class Success(val data: Issue) : IssueDetailsUiState
}