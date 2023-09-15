package com.github.stephenwanjala.smartattend.auth.login.presentation

import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.core.util.UiText

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isUserNameError: UserNameError? = null,
    val isPasswordError: PasswordError? = null,
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val login: AuthResponse? = null,
    val error: UiText? = null
) {
    sealed class UserNameError {
        data object FieldEmpty : UserNameError()
        data object InvalidUserName : UserNameError()
    }

    sealed class PasswordError {
        data object FieldEmpty : PasswordError()
        data object InvalidPassword : PasswordError()
        data object InputTooShort : PasswordError()
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val login: AuthResponse? = null,
    val error: UiText? = null
)

