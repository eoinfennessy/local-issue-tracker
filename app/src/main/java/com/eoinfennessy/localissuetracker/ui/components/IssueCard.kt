package com.eoinfennessy.localissuetracker.ui.components

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eoinfennessy.localissuetracker.R
import com.eoinfennessy.localissuetracker.data.local.database.IssueStatus
import com.eoinfennessy.localissuetracker.utils.issueStatusToIcon
import java.text.DateFormat
import java.util.Date

@Composable
fun IssueCard(
    name: String,
    description: String,
    dateCreated: String,
    status: IssueStatus,
    imageUri: Uri?,
    onClickIssueDetails: () -> Unit,
    onClickDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        IssueCardContent(name, description, dateCreated, status, imageUri, onClickIssueDetails, onClickDelete)
    }
}

@Composable
private fun IssueCardContent(
    name: String,
    description: String,
    dateCreated: String,
    status: IssueStatus,
    imageUri: Uri?,
    onClickIssueDetails: () -> Unit,
    onClickDelete: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    issueStatusToIcon(issueStatus = status),
                    "Issue Status",
                    Modifier.padding(end = 8.dp)
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Text(
                text = "Created $dateCreated", style = MaterialTheme.typography.labelSmall
            )
            if (expanded) {
                if (imageUri != null) {
                    Thumbnail(
                        imageUri = imageUri,
                        contentDescription = "Issue Thumbnail",
                        Modifier.padding(top = 24.dp).size(80.dp),
                    )
                }
                Text(modifier = Modifier.padding(vertical = 16.dp), text = description)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    FilledTonalButton(onClick = onClickIssueDetails) {
                        Text(text = "Show More")
                    }
                    FilledTonalIconButton(onClick = onClickDelete) {
                        Icon(Icons.Filled.Delete, "Delete issue")
                    }
                }
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview
@Composable
fun IssueCardPreview() {
    IssueCard(
        name = "My Issue",
        description = "A description of my issue...",
        dateCreated = DateFormat.getDateTimeInstance().format(Date()),
        status = IssueStatus.IN_PROGRESS,
        imageUri = Uri.EMPTY,
        onClickIssueDetails = {},
        onClickDelete = {}
    )
}