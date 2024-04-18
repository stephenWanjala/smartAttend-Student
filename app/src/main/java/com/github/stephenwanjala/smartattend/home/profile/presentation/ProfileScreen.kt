@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.stephenwanjala.smartattend.home.profile.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.stephenwanjala.smartattend.core.presentation.components.LoadingDialog
import com.github.stephenwanjala.smartattend.destinations.LoginScreenDestination
import com.github.stephenwanjala.smartattend.home.profile.ProfileViewModel
import com.github.stephenwanjala.smartattend.home.profile.domain.model.ProfileData
import com.github.stephenwanjala.smartattend.home.profile.presentation.components.LogoutButton
import com.github.stephenwanjala.smartattend.home.profile.presentation.components.StudentProfileData
import com.github.stephenwanjala.smartattend.location.presentation.components.LocationPermissionWrapper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo


@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    LocationPermissionWrapper {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Profile", textAlign = TextAlign.Center) },
                    navigationIcon = {
                        IconButton(onClick = navigator::navigateUp) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                )

            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { innerPadding ->
            val context = LocalContext.current
            LaunchedEffect(key1 = state.error) {
                state.error?.let {
                    snackbarHostState.showSnackbar(
                        message = it.asString(context),
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Long
                    )
                }
            }

            LaunchedEffect(key1 = state.accessToken) {
                if (state.accessToken == null) {
                    navigator.navigate(LoginScreenDestination) {
                        popUpTo(LoginScreenDestination) {
                            inclusive = true
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {

                Column(modifier = Modifier.fillMaxSize()) {

                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        StudentProfileData(
                            modifier = Modifier.padding(16.dp),
                            profileData = ProfileData(
                                firstName = state.firstName,
                                lastName = state.lastName,
                                email = state.email,
                                phoneNumber = "+254712345678",
                                course = "Computer Science",
                                enrolledUnits = listOf("Software Engineering", "Data Structures"),
                                regnumber = state.regnumber
                            )
                        )

                        LogoutButton(modifier = Modifier, onVent = viewModel::onEvent,
                            navigate = {
                                if (state.accessToken == null || state.refreshToken == null) {
                                    navigator.navigate(LoginScreenDestination) {
                                        popUpTo(LoginScreenDestination) {
                                            inclusive = true
                                        }
                                    }
                                }
                            },
                            enabled = { state.accessToken != null && state.refreshToken != null && !state.isLoading }
                        )
                    }
                }

                AnimatedVisibility(visible = state.isLoading) {
                    LoadingDialog(modifier = Modifier.align(Alignment.Center))
                }

            }
        }

    }

}