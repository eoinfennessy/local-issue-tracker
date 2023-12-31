package com.eoinfennessy.localissuetracker.ui.screens.myIssues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.service.AccountService
import com.eoinfennessy.localissuetracker.data.service.DbService
import com.eoinfennessy.localissuetracker.ui.screens.myIssues.MyIssuesUiState.Success
import com.eoinfennessy.localissuetracker.ui.screens.myIssues.MyIssuesUiState.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyIssuesViewModel @Inject constructor(
    private val dbService: DbService,
    private val accountService: AccountService
) : ViewModel() {

    val uiState: StateFlow<MyIssuesUiState> = dbService
        .userIssues.map<List<Issue>, MyIssuesUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyIssuesUiState.Loading)

    fun deleteIssue(issue: Issue) {
        viewModelScope.launch {
            dbService.delete(issue.id)
        }
    }
}

sealed interface MyIssuesUiState {
    object Loading : MyIssuesUiState
    data class Error(val throwable: Throwable) : MyIssuesUiState
    data class Success(val data: List<Issue>) : MyIssuesUiState
}