package com.eoinfennessy.localissuetracker.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.model.fakeIssues
import java.text.DateFormat

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
fun IssueCardListPreview() {
    IssueCardList(fakeIssues, {}, {}, { true })
}