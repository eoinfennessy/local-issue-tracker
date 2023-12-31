package com.eoinfennessy.localissuetracker.ui.screens.createIssue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.service.DbService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateIssueViewModel @Inject constructor(
    private val dbService: DbService
) : ViewModel() {
    fun addIssue(issue: Issue) {
        viewModelScope.launch {
            dbService.save(issue)
        }
    }
}