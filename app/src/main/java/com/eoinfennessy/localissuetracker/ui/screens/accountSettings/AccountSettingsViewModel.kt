package com.eoinfennessy.localissuetracker.ui.screens.accountSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {

    fun deleteAccount() {
        viewModelScope.launch { accountService.deleteAccount() }
    }
}