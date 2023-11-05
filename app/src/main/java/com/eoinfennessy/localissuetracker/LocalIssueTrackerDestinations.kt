package com.eoinfennessy.localissuetracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface LocalIssueTrackerDestination {
    val icon: ImageVector
    val label: String
    val route: String
}
object Overview : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.Home
    override val label = "Overview"
    override val route = "overview"
}

object Issues : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.List
    override val label = "View Issues"
    override val route = "issues"
}

object CreateIssue : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.Add
    override val label = "Create an Issue"
    override val route = "create-issue"
}

object IssueDetails : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.Workspaces
    override val label = "Issue Details"
    const val idArg = "id"
    override val route = "issue/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType }
    )
}

val drawerDestinations = listOf(Overview, Issues, CreateIssue)
val allDestinations = listOf(Overview, Issues, CreateIssue, IssueDetails)
