package com.github.stephenwanjala.smartattend.home.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ProfileDataItem(modifier: Modifier = Modifier, label: String, value: String) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "$label:",
                textAlign = TextAlign.Center
            )

            Text(
                text = value,
                textAlign = TextAlign.Center
            )

        }
    }
}