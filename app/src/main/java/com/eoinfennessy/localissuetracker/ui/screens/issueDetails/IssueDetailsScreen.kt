package com.eoinfennessy.localissuetracker.ui.screens.issueDetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun IssueDetailsScreen(
    issueId: Int,
    issueDetailsViewModel: IssueDetailsViewModel = hiltViewModel()
) {
    Text(text = "Issue details for issue with ID $issueId")
    val issueDetailsUiState by issueDetailsViewModel.uiState.collectAsStateWithLifecycle()
    if (issueDetailsUiState is IssueDetailsUiState.Success) {
        val issue = (issueDetailsUiState as IssueDetailsUiState.Success).data
    }
}