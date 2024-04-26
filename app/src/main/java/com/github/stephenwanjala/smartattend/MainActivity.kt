@file:OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)

package com.github.stephenwanjala.smartattend


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.stephenwanjala.smartattend.destinations.SingleScheduleDialogDestination
import com.github.stephenwanjala.smartattend.home.BottomBar
import com.github.stephenwanjala.smartattend.home.BottomBarDestination
import com.github.stephenwanjala.smartattend.ui.theme.SmartAttendTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),

                )
        )

        setContent {
            SmartAttendTheme {
                // A surface container using the 'background' color from the theme

                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navHostEngine = rememberAnimatedNavHostEngine(
                    navHostContentAlignment = Alignment.TopCenter,
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = {
                            scaleIn(transformOrigin = TransformOrigin.Center,)
                        },
                        exitTransition = {
                            scaleOut(transformOrigin = TransformOrigin.Center)
                        }
                    )
                )
                val navController = navHostEngine.rememberNavController(bottomSheetNavigator)
                val bottomBarItems: List<BottomBarDestination> = listOf(
                    BottomBarDestination.SCHEDULE,
                    BottomBarDestination.ATTENDANCE_HISTORY,
                    BottomBarDestination.PROFILE
                )
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination

                val showBottomBar = currentDestination?.route in listOf(
                    BottomBarDestination.SCHEDULE.direction.route,
                    BottomBarDestination.ATTENDANCE_HISTORY.direction.route,
                    BottomBarDestination.PROFILE.direction.route,
                    SingleScheduleDialogDestination.route
                )

                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(visible = showBottomBar) {
                            BottomBar(navController = navController, items = bottomBarItems)
                        }
                    }
                ) { paddingVallues ->
                    val paddings = paddingVallues.calculateBottomPadding()
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
//                            .padding(bottom=paddingVallues.calculateBottomPadding()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ModalBottomSheetLayout(
                            bottomSheetNavigator = bottomSheetNavigator,
                            sheetShape = RoundedCornerShape(16.dp),
                        ) {
                            DestinationsNavHost(
                                navController = navController,
                                navGraph = NavGraphs.root,
                                engine = navHostEngine
                            )
                        }
                    }
                }
            }
        }
    }
}

