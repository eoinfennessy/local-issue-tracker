package com.eoinfennessy.localissuetracker.ui.screens.myIssues

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.ui.components.IssueCardList

@Composable
fun MyIssuesScreen(
    onClickIssueDetails: (issueId: String) -> Unit,
    modifier: Modifier = Modifier,
    myIssuesViewModel: MyIssuesViewModel = hiltViewModel()
) {
    val myIssuesUiState by myIssuesViewModel.uiState.collectAsStateWithLifecycle()
    if (myIssuesUiState is MyIssuesUiState.Success) {
        IssueCardList(
            issues = (myIssuesUiState as MyIssuesUiState.Success).data,
            onDeleteIssue = { issue: Issue -> myIssuesViewModel.deleteIssue(issue) },
            onClickIssueDetails = onClickIssueDetails,
            displayDeletePredicate = { _ -> true },
            modifier = modifier
        )
    }
}