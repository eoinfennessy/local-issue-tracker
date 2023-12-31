package com.eoinfennessy.localissuetracker.ui.screens.issues

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.ui.components.IssueCardList

@Composable
fun IssuesScreen(
    onClickIssueDetails: (issueId: String) -> Unit,
    modifier: Modifier = Modifier,
    issuesViewModel: IssuesViewModel = hiltViewModel()
) {
    val issuesUiState by issuesViewModel.uiState.collectAsStateWithLifecycle()
    if (issuesUiState is IssuesUiState.Success) {
        IssueCardList(
            issues = (issuesUiState as IssuesUiState.Success).data,
            onDeleteIssue = { issue: Issue -> issuesViewModel.deleteIssue(issue) },
            onClickIssueDetails = onClickIssueDetails,
            displayDeletePredicate = { issue: Issue -> issue.userId == issuesViewModel.userId },
            modifier = modifier
        )
    }
}