package com.github.stephenwanjala.smartattend.home.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.navigation.compose.rememberNavController
import com.github.stephenwanjala.smartattend.NavGraphs
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.home.BottomBar
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@AuthNavGraph
@Destination
@Composable
fun HomeScreen(
    authResponse: AuthResponse,
) {
    val navController = rememberNavController()
    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = {
                scaleIn(transformOrigin = TransformOrigin(0.25f, 0f))
            },
            exitTransition = {
                scaleOut(transformOrigin = TransformOrigin(0.75f, 1f))
            }
        )
    )
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

                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.home,
                    engine = navHostEngine
                )

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


