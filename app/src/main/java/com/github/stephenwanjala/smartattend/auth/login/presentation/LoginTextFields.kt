package com.github.stephenwanjala.smartattend.auth.login.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.stephenwanjala.smartattend.R
import com.github.stephenwanjala.smartattend.core.presentation.components.AButton
import com.github.stephenwanjala.smartattend.core.presentation.components.ATextButton
import com.github.stephenwanjala.smartattend.core.presentation.components.InputTextField
import com.github.stephenwanjala.smartattend.core.presentation.components.PasswordTextField

@Composable
fun LoginTextFields(
    buttonLabel: String,
    onForgetPassword: () -> Unit,
    viewModel: LoginViewModel,
    onClickLogin: () -> Unit,

    ) {


    val state = viewModel.state.collectAsState().value

    val configuration = LocalConfiguration.current
    var orientation by remember {
        mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT)
    }

    LaunchedEffect(key1 = configuration) {
        snapshotFlow { configuration.orientation }.collect { orientation = it }
    }



    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 32.dp, top = 32.dp)
                                .align(Alignment.CenterHorizontally),
                            text = stringResource(R.string.sign_in_welcome_text),
                            fontWeight = FontWeight.ExtraBold,
                            fontStyle = FontStyle.Normal,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.background,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            InputTextField(
                                textValue = state.userName,
                                labelText = "RegNumber",
                                onValueChange = { viewModel.onEvent(LoginEvent.EnteredUserName(it)) },
                                modifier = Modifier.weight(0.5f),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                isError = state.userNameError != null,
                                supportText = state.userNameError,

                            )


                            PasswordTextField(
                                modifier = Modifier.weight(0.5f),
                                textValue = state.password,
                                labelText = "Password",

                                placeHolder = "Your Password",
                                onValueChange = {
                                    viewModel.onEvent(LoginEvent.EnteredPassword(it))

                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                                ),
                                isError =state.passwordError !=null ,
                                supportText = state.passwordError,

                            )

                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = onForgetPassword,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(end = 64.dp)
                                .align(Alignment.End),
                            contentPadding = PaddingValues(1.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.forgot_password),
                                color = MaterialTheme.colorScheme.surface,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center
                            )
                        }

                        ATextButton(text = stringResource(id = R.string.sign_in),
                            onClick = onClickLogin,
                            modifier = Modifier.fillMaxWidth(0.6f),
                            buttonEnabled = { state.isLoginButtonEnabled }

                        )


                    }
                }
            }


        }

        else -> {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 32.dp, top = 32.dp)
                                .align(Alignment.CenterHorizontally),
                            text = stringResource(R.string.sign_in_welcome_text),
                            fontWeight = FontWeight.ExtraBold,
                            fontStyle = FontStyle.Normal,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.background,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        InputTextField(
                            textValue = state.userName,
                            labelText = "RegNumber",
                            onValueChange = { viewModel.onEvent(LoginEvent.EnteredUserName(it)) },
                            isError = state.userNameError != null,
                            supportText = state.userNameError,

                            )


                        PasswordTextField(
                            textValue = state.password,
                            labelText = "Password",
                            placeHolder = "Your Password",

                            onValueChange = {
                                viewModel.onEvent(LoginEvent.EnteredPassword(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                            ),
                            isError = state.passwordError != null,
                            supportText = state.passwordError,

                            )

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            Spacer(modifier = Modifier.width(8.dp))

                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(
                                onClick = onForgetPassword,
                                modifier = Modifier.wrapContentHeight(),
                                contentPadding = PaddingValues(1.dp),
                            ) {
                                Text(
                                    text = "Forgot password?",
                                    color = MaterialTheme.colorScheme.surface,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                        AButton(
                            text = buttonLabel,
                            onClick = onClickLogin,
                            modifier = Modifier,
                            buttonEnabled = { state.isLoginButtonEnabled }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }


}