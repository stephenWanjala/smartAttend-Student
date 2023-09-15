package com.github.stephenwanjala.smartattend.auth.login.presentation

sealed class LoginEvent {
    data class EnteredUserName(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()
    data object Login : LoginEvent()
}