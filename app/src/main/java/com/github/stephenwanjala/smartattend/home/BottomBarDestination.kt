package com.github.stephenwanjala.smartattend.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.github.stephenwanjala.smartattend.NavGraphs
import com.github.stephenwanjala.smartattend.R
import com.github.stephenwanjala.smartattend.appCurrentDestinationAsState
import com.github.stephenwanjala.smartattend.destinations.AttendanceHistoryDestination
import com.github.stephenwanjala.smartattend.destinations.Destination
import com.github.stephenwanjala.smartattend.destinations.ProfileScreenDestination
import com.github.stephenwanjala.smartattend.destinations.SchedulesScreenDestination
import com.github.stephenwanjala.smartattend.startAppDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val label: Int
) {
    SCHEDULE(
        selectedIcon = Icons.Filled.Schedule,
        unselectedIcon = Icons.Outlined.Schedule,
        label = R.string.schedule,
        direction = SchedulesScreenDestination
    ),

    ATTENDANCE_HISTORY(
        selectedIcon = Icons.Filled.HistoryEdu,
        unselectedIcon = Icons.Outlined.HistoryEdu,
        label = R.string.attendance_history,
        direction = AttendanceHistoryDestination
    ),
    PROFILE(
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        label = R.string.profile,
        direction = ProfileScreenDestination
    ),
}


@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.home.startAppDestination

    NavigationBar {
        BottomBarDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction, fun NavOptionsBuilder.() {
                        launchSingleTop = true
                        restoreState = true
                    })
                },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == destination.direction) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}