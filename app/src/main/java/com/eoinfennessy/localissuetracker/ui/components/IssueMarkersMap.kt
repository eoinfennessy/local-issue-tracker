package com.eoinfennessy.localissuetracker.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eoinfennessy.localissuetracker.data.local.database.Issue
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun IssueMarkersMap(
    issues: List<Issue>,
    modifier: Modifier = Modifier,
    onInfoWindowClick: (issueId: Int) -> Unit = {}
) {
    val initialLocation = if (issues.isEmpty()) {
        LatLng(0.0, 0.0)
    } else {
        LatLng(issues[0].latitude, issues[0].longitude)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLocation, 10f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        for (issue in issues) {
            val position = LatLng(issue.latitude, issue.longitude)
            Marker(
                state = MarkerState(position = position),
                title = issue.name,
                snippet = "${issue.description.take(20)}...",
                onInfoWindowClick = { onInfoWindowClick(issue.id) }
            )
        }
    }
}