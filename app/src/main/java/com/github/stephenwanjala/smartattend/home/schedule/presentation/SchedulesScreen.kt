package com.github.stephenwanjala.smartattend.home.schedule.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.stephenwanjala.smartattend.core.presentation.components.LoadingDialog
import com.github.stephenwanjala.smartattend.destinations.SingleScheduleDialogDestination
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.LectureScheduleItem
import com.github.stephenwanjala.smartattend.location.presentation.components.LocationPermissionWrapper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun SchedulesScreen(
    navigator: DestinationsNavigator,
    viewModel: SchedulesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LocationPermissionWrapper {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "Schedules")
                        }

                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.schedules) { schedule ->
                                ScheduleItem(schedule = schedule, onClick = {
                                    navigator.navigate(SingleScheduleDialogDestination(schedule))
                                })
                            }

                        }
                    }

                    AnimatedVisibility(visible = state.isLoading) {
                        LoadingDialog(modifier = Modifier.align(Alignment.Center))
                    }
                }

            }

        }
    }
    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(state.error.asString(context = context))
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleItem(
    schedule: LectureScheduleItem, modifier: Modifier = Modifier,
    onClick: (LectureScheduleItem) -> Unit
) {
    BadgedBox(badge = {
        Badge(
            containerColor = schedule.statusColor.container,
            contentColor = schedule.statusColor.contentColor
        ) {
            Text(text = schedule.status,)
        }
    }, modifier = modifier.padding(8.dp)) {

        OutlinedCard(
            onClick = { onClick(schedule) }, modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(text = "Unit: ${schedule.unit.unit_code} - ${schedule.unit.unit_name}")
                Text(text = "Venue: ${schedule.lecture_hall.name}")
                Text(text = "Lecturer: ${schedule.lecturer.fullName}")
                Text(
                    text = "Scheduled on Date: ${schedule.date}",
                    modifier = Modifier.align(Alignment.End)
                )

            }
        }
    }
}
