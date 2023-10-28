package com.eoinfennessy.localissuetracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eoinfennessy.localissuetracker.ui.screens.createIssue.CreateIssueScreen
import com.eoinfennessy.localissuetracker.ui.screens.overview.OverviewScreen
import com.eoinfennessy.localissuetracker.ui.screens.issues.IssuesScreen

@Composable
fun LocalIssueTrackerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ) {
        composable(route = Overview.route) {
            OverviewScreen()
        }
        composable(route = CreateIssue.route) {
            CreateIssueScreen()
        }
        composable(route = Issues.route) {
            IssuesScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }