package com.eoinfennessy.localissuetracker.ui.screens.signOut

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignOutScreen(
    modifier: Modifier = Modifier,
    signOutViewModel: SignOutViewModel = hiltViewModel()
) {
    signOutViewModel.signOut()
    Text(text = "User has been signed out...")
}