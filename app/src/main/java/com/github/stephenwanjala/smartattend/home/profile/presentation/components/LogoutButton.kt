package com.github.stephenwanjala.smartattend.home.profile.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.stephenwanjala.smartattend.core.presentation.components.AButton
import com.github.stephenwanjala.smartattend.home.profile.ProfileEvent

@Composable
fun LogoutButton(
    modifier: Modifier = Modifier,
    onVent: (ProfileEvent) -> Unit,
    navigate: () -> Unit,
    enabled: ()->Boolean
) {
    AButton(text = "Logout", onClick = {
        onVent(ProfileEvent.Logout)

        navigate()
    }, modifier = modifier, buttonEnabled = enabled)

}