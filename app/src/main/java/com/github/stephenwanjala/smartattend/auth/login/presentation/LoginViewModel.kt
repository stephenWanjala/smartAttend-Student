package com.github.stephenwanjala.smartattend.auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginUiState())

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredUserName -> {
                _state.update { it.copy(userName = event.value) }
                _state.update {
                    it.copy(
                        isLoginButtonEnabled = (checkUserNameAndPassword(
                            userName = state.value.userName,
                            password = state.value.password
                        ) && checkValidRegNumber(state.value.userName))
                    )
                }
            }

            is LoginEvent.EnteredPassword -> {
                _state.update { it.copy(password = event.value) }
                _state.update {
                    it.copy(
                        isLoginButtonEnabled = (checkUserNameAndPassword(
                            userName = state.value.userName,
                            password = state.value.password
                        ) && checkValidRegNumber(state.value.userName))
                    )
                }
            }

            LoginEvent.TogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !state.value.isPasswordVisible) }
            }

            LoginEvent.Login -> {

            }
        }
    }


    private fun checkValidRegNumber(regNo: String): Boolean {
        val regex = Regex("""^([A-Za-z]+)\/(\d{5})\/(\d{3})$""")
        return regex.matches(regNo)
    }

    private fun checkUserNameAndPassword(userName: String, password: String): Boolean =
        userName.isNotBlank() && userName.isNotEmpty()
                && password.isNotBlank() && password.isNotEmpty()
                && password.length >= 8
}