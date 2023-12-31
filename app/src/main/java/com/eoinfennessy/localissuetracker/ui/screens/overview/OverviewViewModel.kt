package com.eoinfennessy.localissuetracker.ui.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.service.DbService
import com.eoinfennessy.localissuetracker.ui.screens.overview.OverviewUiState.Error
import com.eoinfennessy.localissuetracker.ui.screens.overview.OverviewUiState.Loading
import com.eoinfennessy.localissuetracker.ui.screens.overview.OverviewUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val dbService: DbService
) : ViewModel() {

    val uiState: StateFlow<OverviewUiState> = dbService
        .issues.map<List<Issue>, OverviewUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
}

sealed interface OverviewUiState {
    object Loading : OverviewUiState
    data class Error(val throwable: Throwable) : OverviewUiState
    data class Success(val data: List<Issue>) : OverviewUiState
}