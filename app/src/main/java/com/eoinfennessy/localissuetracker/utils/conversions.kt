package com.eoinfennessy.localissuetracker.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.IncompleteCircle
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.eoinfennessy.localissuetracker.data.model.IssueStatus

fun issueStatusStringToEnum(issueStatus: String): IssueStatus {
    return when (issueStatus) {
        "Open" -> IssueStatus.OPEN
        "In Progress" -> IssueStatus.IN_PROGRESS
        "Closed" -> IssueStatus.CLOSED
        else -> throw Error("Invalid issue status string provided: $issueStatus")
    }
}

@Composable
fun issueStatusToIcon(issueStatus: IssueStatus): ImageVector {
    return when (issueStatus) {
        IssueStatus.OPEN -> Icons.Filled.LightbulbCircle
        IssueStatus.IN_PROGRESS -> Icons.Filled.IncompleteCircle
        IssueStatus.CLOSED -> Icons.Filled.CheckCircle
    }
}