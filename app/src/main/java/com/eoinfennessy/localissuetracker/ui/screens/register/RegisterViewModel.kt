package com.eoinfennessy.localissuetracker.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {

    fun linkAccount(email: String, password: String) {
        viewModelScope.launch { accountService.linkAccount(email, password) }
    }
}