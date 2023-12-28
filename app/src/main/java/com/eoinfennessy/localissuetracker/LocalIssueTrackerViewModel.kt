package com.eoinfennessy.localissuetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalIssueTrackerViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {

    val currentUser = accountService.currentUser

    fun createAnonymousAccountIfNoUser() {
        if (!accountService.hasUser) {
            viewModelScope.launch { accountService.createAnonymousAccount() }
        }
    }
}