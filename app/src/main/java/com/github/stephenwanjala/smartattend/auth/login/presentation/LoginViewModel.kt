package com.github.stephenwanjala.smartattend.auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthRequest
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.auth.login.domain.repository.AuthRepository
import com.github.stephenwanjala.smartattend.core.util.Resource
import com.github.stephenwanjala.smartattend.core.util.UiText
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferences: SmartAttendPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginUiState())


    init {
        authenticate()
    }

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
                _state.update { it.copy(isLoading = true) }

                viewModelScope.launch {
                    try {
                        val result = authRepository.login(
                            AuthRequest(
                                reg_number = state.value.userName,
                                password = state.value.password
                            )
                        )
                        println("The Result is $result  ")
                        result.collectLatest { resFlow ->
                            when (resFlow) {
                                is Resource.Error -> {

                                    _state.update { it.copy(error = resFlow.uiText) }
                                }

                                is Resource.Loading -> {
                                    _state.update { it.copy(isLoading = true) }
                                    println("Loading In Vm")

                                }

                                is Resource.Success -> {
                                    _state.update { it.copy(login = resFlow.data) }
                                    println("We got Data")
                                }
                            }
                            _state.update { it.copy(isLoading = false) }
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(e.localizedMessage ?: "Unknown Error ")
                            )
                        }

                    }

                }
            }
        }
    }


    private fun authenticate() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            preferences.getToken().collectLatest { tkData ->
                if (tkData != null && tkData.access.isNotBlank() && tkData.refresh.isNotBlank()) {
                    _state.update {
                        it.copy(
                            login = AuthResponse(
                                access = tkData.access,
                                refresh = tkData.refresh,
                                user_id = tkData.user_id,
                                reg_number = tkData.reg_number,
                                first_name = tkData.first_name,
                                last_name = tkData.last_name,
                                email = tkData.email
                            ),
                            isLoading = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = UiText.DynamicString("Please Login")
                        )
                    }
                }
            }
            _state.update { it.copy(isLoading = false) }
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

