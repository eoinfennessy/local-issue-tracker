package com.eoinfennessy.localissuetracker.ui.screens.issueDetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun IssueDetailsScreen(issueId: Int) {
    Text(text = "Issue details for issue with ID $issueId")
}