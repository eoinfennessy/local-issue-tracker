package com.eoinfennessy.localissuetracker.ui.screens.accountSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountSettingsScreen(
    modifier: Modifier = Modifier,
    onDeleteAccount: () -> Unit = {},
    accountSettingsViewModel: AccountSettingsViewModel = hiltViewModel()
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                accountSettingsViewModel.deleteAccount()
                onDeleteAccount()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
        ) {
            Text(text = "Delete Account")
        }
    }
}