package com.eoinfennessy.localissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eoinfennessy.localissuetracker.ui.theme.LocalIssueTrackerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalIssueTrackerApp()
        }
    }
}

@Composable
fun LocalIssueTrackerApp() {
    LocalIssueTrackerTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            drawerDestinations.find { it.route == currentDestination?.route } ?: Overview
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Drawer title", modifier = Modifier.padding(16.dp))
                    Divider()
                    drawerDestinations.forEach {
                        NavigationDrawerItem(
                            label = { Text(text = it.label) },
                            icon = { Icon(it.icon, it.route) },
                            selected = it.route == currentScreen.route,
                            onClick = {
                                navController.navigateSingleTopTo(it.route)
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            },
        ) {
            Scaffold(
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