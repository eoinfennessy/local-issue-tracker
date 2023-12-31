package com.eoinfennessy.localissuetracker.ui.screens.issues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.service.DbService
import com.eoinfennessy.localissuetracker.ui.screens.issues.IssuesUiState.Error
import com.eoinfennessy.localissuetracker.ui.screens.issues.IssuesUiState.Loading
import com.eoinfennessy.localissuetracker.ui.screens.issues.IssuesUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val dbService: DbService
) : ViewModel() {

    val uiState: StateFlow<IssuesUiState> = dbService
        .issues.map<List<Issue>, IssuesUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun deleteIssue(issue: Issue) {
        viewModelScope.launch {
            dbService.delete(issue.id)
        }
    }
}

sealed interface IssuesUiState {
    object Loading : IssuesUiState
    data class Error(val throwable: Throwable) : IssuesUiState
    data class Success(val data: List<Issue>) : IssuesUiState
}