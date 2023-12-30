package com.eoinfennessy.localissuetracker.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {

    fun authenticate(email: String, password: String) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
//                TODO: display throwable.message on snackbar
            },
        ) { accountService.authenticate(email, password) }
    }
}