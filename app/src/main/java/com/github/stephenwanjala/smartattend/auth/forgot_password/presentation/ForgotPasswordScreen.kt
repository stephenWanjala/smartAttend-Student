package com.github.stephenwanjala.smartattend.auth.forgot_password.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.stephenwanjala.smartattend.R
import com.github.stephenwanjala.smartattend.core.presentation.components.AButton
import com.github.stephenwanjala.smartattend.core.presentation.components.IConWithText
import com.github.stephenwanjala.smartattend.core.presentation.components.InputTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Destination
@Composable
fun ForgotPasswordScreen(
    navigator: DestinationsNavigator
) {
    var emailState by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Surface(color = MaterialTheme.colorScheme.background) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },

            ) { paddingValue ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                contentAlignment = Alignment.Center
            ) {
                IConWithText(
                    modifier = Modifier.align(Alignment.TopCenter),
                    text = stringResource(R.string.forgot_password_),
                    onClick = navigator::navigateUp
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(12.dp),

                    ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.forgot_password_text),
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            textAlign = TextAlign.Center
                        )
                        InputTextField(
                            textValue = emailState,
                            labelText = "Your Email",
                            onValueChange = { email ->
                                emailState = email

                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Send
                            )
                        )

                        AButton(
                            text = "Send",
                            onClick = {
                                scope.launch {
                                    snackBarHostState
                                        .showSnackbar(
                                            message = "Sent, check your email",
                                            actionLabel = "Dismiss",
                                            duration = SnackbarDuration.Long
                                        )
                                }

                                emailState = ""
                            },

                            modifier = Modifier.padding(bottom = 8.dp),
                            buttonEnabled = ({
                                emailState.isNotBlank() &&
                                        android.util.Patterns.EMAIL_ADDRESS.matcher(emailState)
                                            .matches()
                            }
                                    )
                        )
                    }
                }

            }

        }

    }
}