package com.eoinfennessy.localissuetracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
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
        navArgument(idArg) { type = NavType.StringType }
    )
}

object Login : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.Login
    override val label = "Login"
    override val route = "login"
}

object Register : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.AppRegistration
    override val label = "Register"
    override val route = "register"
}

object AccountSettings : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.ManageAccounts
    override val label = "Account Settings"
    override val route = "account-settings"
}

object SignOut : LocalIssueTrackerDestination {
    override val icon = Icons.Filled.Logout
    override val label = "Sign Out"
    override val route = "sign-out"
}

val drawerDestinations = listOf(Overview, Issues, CreateIssue)
val userSignedOutDestinations = listOf(Login, Register)
val userLoggedInDestinations = listOf(AccountSettings, SignOut)
val allDestinations = listOf(
    Overview, Issues, CreateIssue, IssueDetails, Login, Register, AccountSettings, SignOut,
)
