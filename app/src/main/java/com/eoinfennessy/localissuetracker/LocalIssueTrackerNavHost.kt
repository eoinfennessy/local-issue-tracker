package com.eoinfennessy.localissuetracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eoinfennessy.localissuetracker.ui.screens.createIssue.CreateIssueScreen
import com.eoinfennessy.localissuetracker.ui.screens.issueDetails.IssueDetailsScreen
import com.eoinfennessy.localissuetracker.ui.screens.overview.OverviewScreen
import com.eoinfennessy.localissuetracker.ui.screens.issues.IssuesScreen
import com.eoinfennessy.localissuetracker.ui.screens.login.LoginScreen
import com.eoinfennessy.localissuetracker.ui.screens.signOut.SignOutScreen
import com.eoinfennessy.localissuetracker.ui.screens.register.RegisterScreen

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
            OverviewScreen(onMapMarkerInfoWindowClick = { issueId -> navController.navigateToIssueDetails(issueId) } )
        }
        composable(route = CreateIssue.route) {
            CreateIssueScreen(onSubmitForm = { navController.popBackStack() })
        }
        composable(route = Issues.route) {
            IssuesScreen(onClickIssueDetails = { issueId -> navController.navigateToIssueDetails(issueId) } )
        }
        composable(route = Login.route) {
            LoginScreen(onLogin = { navController.popBackStack() })
        }
        composable(route = Register.route) {
            RegisterScreen(onRegister = { navController.popBackStack() })
        }
        composable(route = SignOut.route) {
            SignOutScreen()
        }
        composable(
            route = IssueDetails.route,
            arguments = IssueDetails.arguments,
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(IssueDetails.idArg)
            if (id != null) {
                IssueDetailsScreen()
            }
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

private fun NavHostController.navigateToIssueDetails(issueId: Int) {
    this.navigate("issue/$issueId")
}