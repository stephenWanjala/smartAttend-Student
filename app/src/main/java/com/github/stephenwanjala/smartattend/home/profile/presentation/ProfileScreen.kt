package com.github.stephenwanjala.smartattend.home.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.stephenwanjala.smartattend.home.presentation.HomeNavGraph
import com.github.stephenwanjala.smartattend.location.presentation.components.LocationPermissionWrapper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HomeNavGraph
@Destination
@Composable
fun ProfileScreen(
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
                Text(text = "Profile Screen")
            }
        }
    }
}