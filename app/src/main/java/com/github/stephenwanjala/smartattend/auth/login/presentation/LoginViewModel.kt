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
                        ) && checkValidRegNumber(state.value.userName)),
                        userNameError = if (checkValidRegNumber(state.value.userName)) {
                            null
                        } else {
                            "Invalid Registration Number"
                        }
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
                        ) && checkValidRegNumber(state.value.userName)),
                        passwordError = if (shorPassword(state.value.password)) {
                            "Password is too short"
                        } else {
                            null
                        }
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
        val regex = Regex("""^([A-Za-z]{3})\/(?!0{5})(\d{5})\/(\d{3})$""")
        return regex.matchEntire(regNo) != null
    }

    private fun checkUserNameAndPassword(userName: String, password: String): Boolean =
        userName.isNotBlank() && userName.isNotEmpty()
                && password.isNotBlank() && password.isNotEmpty()
                && password.length >= 8
}

fun validatePassword(password: String): List<String> {
    val errors = mutableListOf<String>()

    val digitRegex = Regex("(?=.*[0-9])")
    val lowercaseRegex = Regex("(?=.*[a-z])")
    val uppercaseRegex = Regex("(?=.*[A-Z])")
    val specialCharRegex = Regex("(?=.*\\W)")
    val spaceRegex = Regex("\\s")
    val lengthRegex = Regex(".{8,16}")

    if (!digitRegex.containsMatchIn(password)) {
        errors.add("At least one digit (0-9) is required.")
    }

    if (!lowercaseRegex.containsMatchIn(password)) {
        errors.add("At least one lowercase letter (a-z) is required.")
    }

    if (!uppercaseRegex.containsMatchIn(password)) {
        errors.add("At least one uppercase letter (A-Z) is required.")
    }

    if (!specialCharRegex.containsMatchIn(password)) {
        errors.add("At least one special character is required.")
    }

    if (spaceRegex.containsMatchIn(password)) {
        errors.add("Whitespace is not allowed.")
    }

    if (!lengthRegex.matches(password)) {
        errors.add("Password length should be between 8 and 16 characters.")
    }

    return errors
}


private fun shorPassword(password: String): Boolean = password.length < 8

