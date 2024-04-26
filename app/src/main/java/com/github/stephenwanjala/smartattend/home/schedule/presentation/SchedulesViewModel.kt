package com.github.stephenwanjala.smartattend.home.schedule.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.stephenwanjala.smartattend.core.util.Resource
import com.github.stephenwanjala.smartattend.core.util.UiText
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.LectureScheduleItem
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.TokenHeader
import com.github.stephenwanjala.smartattend.home.schedule.domain.repository.ScheduleRepository
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulesViewModel @Inject constructor(
    private val schedulesRepository: ScheduleRepository,
    private val prefs: SmartAttendPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(ScheduleState())
    val state: StateFlow<ScheduleState> =
        _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), ScheduleState())

    init {
        loadSchedules()
    }

    fun dispatch(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.OnLoad -> loadSchedules()
            is ScheduleEvent.OnRefresh -> loadSchedules()
        }
    }


    private fun loadSchedules() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            prefs.getToken().collectLatest { tokenData ->
                tokenData?.let { data ->
                    println(":Token Data ${data.access}")
                    if (data.access.isNotBlank()) {
                        schedulesRepository.getSchedules(tokenHeader = TokenHeader(accessToken = data.access))
                            .collectLatest { response ->
                                println("The Response is $response")
                                when (response) {
                                    is Resource.Error -> {
                                        _state.update {
                                            it.copy(
                                                isLoading = false,
                                                error = response.uiText
                                            )
                                        }
                                        println("The Error is ${response.uiText}")
                                        println("The state is ${_state.value}")
                                    }

                                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }

                                    is Resource.Success -> {
                                        response.data?.let { schedules ->
                                            print("The Schedules are $schedules")
                                            println("The state is ${_state.value}")
                                            _state.update {
                                                it.copy(
                                                    isLoading = false,
                                                    schedules = schedules
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                    }

                }
            }
        }


    }
}


data class ScheduleState(
    val isLoading: Boolean = false,
    val schedules: List<LectureScheduleItem> = emptyList(),
    val error: UiText? = null
)

sealed class ScheduleEvent {
    data object OnLoad : ScheduleEvent()
    data object OnRefresh : ScheduleEvent()
}
