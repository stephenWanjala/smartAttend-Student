package com.github.stephenwanjala.smartattend.location.presentation

sealed interface LocationEvent {
    data object LocationEnabled : LocationEvent
    data object LocationDisabled : LocationEvent

}