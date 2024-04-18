package com.github.stephenwanjala.smartattend.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.stephenwanjala.smartattend.auth.login.domain.model.Token
import com.github.stephenwanjala.smartattend.auth.login.domain.repository.AccessToken
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
class ProfileViewModel @Inject constructor(
    private val preferences: SmartAttendPreferences,
    private val auth: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state =
        _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), ProfileUiState())


    init {
        loadDataFromPref()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadProfile -> loadDataFromPref()
            is ProfileEvent.Logout -> logout()
        }
    }


    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            state.value.accessToken?.let { access ->
                state.value.refreshToken?.let { refresh ->
                    Token(
                        access = access,
                        refresh = refresh
                    )
                }
            }?.let { token ->
                auth.logout(
                    token
                ).collectLatest { response ->
                    when (response) {
                        is Resource.Error -> _state.update { it.copy(isLoading = false, error = response.uiText) }
                        is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                        is Resource.Success -> _state.update { ProfileUiState() }
                    }
                }
            }

        }
    }


    private fun loadDataFromPref() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            preferences.getToken().collectLatest { tokenData ->
                tokenData?.let { data ->
                    if (data.access.isNotBlank()) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                email = data.email,
                                firstName = data.first_name,
                                lastName = data.last_name,
                                regnumber = data.reg_number,
                                accessToken = data.access,
                                refreshToken = data.refresh
                            )
                        }
                    }
                }
            }
        }

    }
}


data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val course: String = "",
    val enrolledUnits: List<String> = emptyList(),
    val regnumber: String = "",
    val isLoading: Boolean = false,
    val accessToken: AccessToken? = null,
    val refreshToken: String? = null,
    val error :UiText? = null
)


sealed interface ProfileEvent {
    data object LoadProfile : ProfileEvent
    data object Logout : ProfileEvent
}