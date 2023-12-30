package com.eoinfennessy.localissuetracker.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.eoinfennessy.localissuetracker.utils.validateEmail
import com.eoinfennessy.localissuetracker.utils.validateNewPassword

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit = {},
    onSendRecoveryEmail: () -> Unit = {},
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var emailIsValid by remember { mutableStateOf<Boolean?>(null) }
    var password by remember { mutableStateOf("") }
    var passwordIsValid by remember { mutableStateOf<Boolean?>(null) }

    fun validateForm(): Boolean {
        emailIsValid = validateEmail(email)
        passwordIsValid = validateNewPassword(password)
        if (emailIsValid == false) return false
        if (passwordIsValid == false) return false
        return true
    }

    Column(
        Modifier.fillMaxHeight(0.5f).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            AnimatedVisibility(visible = (emailIsValid == false)) {
                Text(
                    text = "Email cannot be empty",
                    style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.error)
                )
            }
            TextField(
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Email") },
                value = email,
                onValueChange = { email = it; emailIsValid = validateEmail(it) },
                isError = (emailIsValid == false)
            )
        }

        Column {
            AnimatedVisibility(visible = (passwordIsValid == false)) {
                Text(
                    text = "Password cannot be empty",
                    style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.error)
                )
            }
            TextField(
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
                value = password,
                onValueChange = { password = it; passwordIsValid = validateNewPassword(it) },
                isError = (passwordIsValid == false),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Button(onClick = {
            if (validateForm()) {
                loginViewModel.authenticate(email, password)
                onLogin()
            }
        }
        ) {
            Text(text = "Login")
        }

        OutlinedButton(onClick = {
            if (validateEmail(email)) {
                loginViewModel.sendRecoveryEmail(email)
                onSendRecoveryEmail()
            }
        }
        ) {
            Text(text = "Forgot Password?")
        }
    }
}