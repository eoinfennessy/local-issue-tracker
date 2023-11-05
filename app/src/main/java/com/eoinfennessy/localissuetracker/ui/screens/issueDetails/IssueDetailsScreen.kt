package com.eoinfennessy.localissuetracker.ui.screens.issueDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eoinfennessy.localissuetracker.ui.components.IssueMarkersMap
import com.eoinfennessy.localissuetracker.utils.issueStatusToIcon
import java.text.DateFormat

@Composable
fun IssueDetailsScreen(
    issueDetailsViewModel: IssueDetailsViewModel = hiltViewModel()
) {
    val issueDetailsUiState by issueDetailsViewModel.uiState.collectAsStateWithLifecycle()
    if (issueDetailsUiState is IssueDetailsUiState.Success) {
        val issue = (issueDetailsUiState as IssueDetailsUiState.Success).data
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
            ) {
                Card {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                issueStatusToIcon(issueStatus = issue.status),
                                "Issue Status",
                                Modifier.padding(end = 8.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = issue.name,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = "Created ${
                                DateFormat.getDateTimeInstance().format(issue.dateCreated)
                            }", style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = issue.description
                        )
                    }
                }

                if (issue.imageUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(issue.imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Issue Image",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(5))
                            .fillMaxHeight(0.5f)
                    )
                }
                
                IssueMarkersMap(
                    issues = listOf(issue),
                    modifier = Modifier.clip(RoundedCornerShape(5))
                )
            }
        }
    }
}