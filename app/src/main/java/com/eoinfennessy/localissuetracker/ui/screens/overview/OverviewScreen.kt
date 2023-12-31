package com.eoinfennessy.localissuetracker.ui.screens.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PinDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.ui.components.IssueMarkersMap

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    onMapMarkerInfoWindowClick: (issueId: String) -> Unit = {},
    overviewViewModel: OverviewViewModel = hiltViewModel()
) {
    val overviewUiState by overviewViewModel.uiState.collectAsStateWithLifecycle()
    if (overviewUiState is OverviewUiState.Success) {
        val issues = (overviewUiState as OverviewUiState.Success).data
        OverviewScreenContent(
            issues = issues,
            onMapMarkerInfoWindowClick = onMapMarkerInfoWindowClick,
            modifier = modifier
        )
    }
}

@Composable
fun OverviewScreenContent(
    issues: List<Issue>,
    modifier: Modifier = Modifier,
    onMapMarkerInfoWindowClick: (issueId: String) -> Unit = {},
) {
    Column(
        modifier.fillMaxSize(),
        Arrangement.SpaceEvenly,
        Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.TwoTone.PinDrop,
                "Issues Map",
                Modifier.padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                "Issues Map",
                style = MaterialTheme.typography.headlineMedium,
            )
        }

        IssueMarkersMap(
            issues = issues,
            modifier = modifier
                .fillMaxSize(0.8f)
                .clip(RoundedCornerShape(5)),
            onInfoWindowClick = onMapMarkerInfoWindowClick
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Text(
                "Displaying ${issues.size} issues",
                style = MaterialTheme.typography.labelMedium,
                modifier = modifier.padding(6.dp),
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}