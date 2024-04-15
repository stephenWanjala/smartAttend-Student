package com.github.stephenwanjala.smartattend.auth.login.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.stephenwanjala.smartattend.R
import com.github.stephenwanjala.smartattend.auth.login.presentation.LoginEvent
import com.github.stephenwanjala.smartattend.auth.login.presentation.LoginTextFields
import com.github.stephenwanjala.smartattend.auth.login.presentation.LoginViewModel
import com.github.stephenwanjala.smartattend.core.presentation.components.LoadingDialog
import com.github.stephenwanjala.smartattend.destinations.ForgotPasswordScreenDestination
import com.github.stephenwanjala.smartattend.destinations.HomeScreenDestination
import com.github.stephenwanjala.smartattend.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Composable
@Destination(start = true)
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        val state = viewModel.state.collectAsState()
        print("The state is $state\n")

        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(key1 = state.value.login) {
            print("The state is $state\n")
            state.value.login?.let { authResponse ->
                println("The AuthResponse is $authResponse\n")
                navigator.navigate(HomeScreenDestination) {
                    popUpTo(LoginScreenDestination) {
                        inclusive = true
                    }
                }
            }
        }

        val context = LocalContext.current
        LaunchedEffect(state.value.error) {
            state.value.error?.let { uiText ->
                snackbarHostState.showSnackbar(
                    message = uiText.asString(context),
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
            }
        }

        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues),
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .align(Alignment.Center),
                        shape = RoundedCornerShape(12.dp),

                        ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            LoginTextFields(
                                buttonLabel = stringResource(id = R.string.sign_in),
                                onForgetPassword = {
                                    navigator.navigate(ForgotPasswordScreenDestination) {
                                        popUpTo(LoginScreenDestination) {
                                            inclusive = true

                                        }
                                    }
                                },
                                viewModel = viewModel,
                                onClickLogin = {
                                    viewModel.onEvent(LoginEvent.Login)
                                    print("The state is $state\n")
                                }
                            )

                        }
                    }

                    AnimatedVisibility(
                        visible = (state.value.isLoading), modifier = Modifier.align(
                            Alignment.Center
                        )
                    ) {
                        LoadingDialog()

                    }
                }
            }
        }
    }
}