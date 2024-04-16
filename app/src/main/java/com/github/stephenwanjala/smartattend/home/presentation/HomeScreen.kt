package com.github.stephenwanjala.smartattend.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.github.stephenwanjala.smartattend.NavGraphs
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.home.BottomBar
import com.github.stephenwanjala.smartattend.location.presentation.components.LocationPermissionWrapper
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph

@AuthNavGraph
@Destination
@Composable
fun HomeScreen(
    authResponse: AuthResponse,
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            LocationPermissionWrapper {

                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.home
                )

            }
        }
    }
}

@NavGraph
annotation class HomeNavGraph(
    val start: Boolean = false
)

@NavGraph
annotation class AuthNavGraph(
    val start: Boolean = false
)


