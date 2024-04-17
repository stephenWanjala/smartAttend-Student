package com.github.stephenwanjala.smartattend.home.history.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.github.stephenwanjala.smartattend.home.presentation.HomeNavGraph
import com.github.stephenwanjala.smartattend.location.presentation.components.LocationPermissionWrapper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@HomeNavGraph
@Composable
fun AttendanceHistory(
    navigator: DestinationsNavigator
) {
    LocationPermissionWrapper {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Attendance History",
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}