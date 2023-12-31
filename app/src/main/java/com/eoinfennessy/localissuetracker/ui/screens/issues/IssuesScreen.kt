package com.eoinfennessy.localissuetracker.ui.screens.issues

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.model.fakeIssues
import com.eoinfennessy.localissuetracker.ui.components.IssueCard
import java.text.DateFormat

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

@Composable
internal fun IssueCardList(
    issues: List<Issue>,
    onClickIssueDetails: (issueId: String) -> Unit,
    onDeleteIssue: (Issue) -> Unit,
    displayDeletePredicate: (Issue) -> Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn() {
        items(
            items = issues,
            key = { issue -> issue.name }
        ) { issue ->
            IssueCard(
                name = issue.name,
                description = issue.description,
                dateCreated = DateFormat.getDateTimeInstance().format(issue.dateCreated),
                status = issue.status,
                imageUri = issue.imageUri,
                onClickIssueDetails = { onClickIssueDetails(issue.id) },
                onClickDelete = { onDeleteIssue(issue) },
                displayDelete = displayDeletePredicate(issue)
            )
        }
    }
}

@Preview
@Composable
fun IssuesScreenPreview() {
    IssueCardList(fakeIssues, {}, {}, { true })
}