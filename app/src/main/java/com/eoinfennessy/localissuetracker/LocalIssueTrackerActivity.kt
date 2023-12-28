package com.eoinfennessy.localissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eoinfennessy.localissuetracker.ui.theme.LocalIssueTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocalIssueTrackerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalIssueTrackerApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalIssueTrackerApp(
    localIssueTrackerViewModel: LocalIssueTrackerViewModel = hiltViewModel()
) {
    LocalIssueTrackerTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            allDestinations.find { it.route == currentDestination?.route } ?: Overview
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        LaunchedEffect(true) {
            localIssueTrackerViewModel.createAnonymousAccountIfNoUser()
        }
        val currentUser by localIssueTrackerViewModel.currentUser.collectAsStateWithLifecycle(
            initialValue = null
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Local Issue Tracker", modifier = Modifier.padding(16.dp))
                    Divider()
                    drawerDestinations.forEach {
                        NavigationDrawerItem(
                            label = { Text(text = it.label) },
                            icon = { Icon(it.icon, it.route) },
                            selected = it.route == currentScreen.route,
                            onClick = {
                                navController.navigate(it.route) { launchSingleTop = true }
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                    Divider()
                    userDestinations.forEach {
                        NavigationDrawerItem(
                            label = { Text(text = it.label) },
                            icon = { Icon(it.icon, it.route) },
                            selected = it.route == currentScreen.route,
                            onClick = {
                                navController.navigate(it.route) { launchSingleTop = true }
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            },
        ) {
            Scaffold(
                topBar = {
                         TopAppBar(
                             title = { Text(text = currentScreen.label) },
                             colors = TopAppBarDefaults.topAppBarColors(
                                 containerColor = MaterialTheme.colorScheme.primaryContainer
                             ),
                             navigationIcon =  {
                                 IconButton(
                                     content = {
                                         Icon(Icons.Filled.Menu, contentDescription = "Menu")
                                     },
                                     onClick = {
                                         scope.launch {
                                             drawerState.apply {
                                                 if (isClosed) open() else close()
                                             }
                                         }
                                     }
                                 )
                             }
                         )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        content = { Icon(Icons.Filled.Menu, contentDescription = "Menu") },
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    )
                }
            ) { contentPadding ->
                LocalIssueTrackerNavHost(
                    navController = navController,
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocalIssueTrackerAppPreview() {
    LocalIssueTrackerApp()
}