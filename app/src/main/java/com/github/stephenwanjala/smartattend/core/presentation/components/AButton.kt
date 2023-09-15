package com.github.stephenwanjala.smartattend.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    buttonEnabled: () -> Boolean,
    leadingIcon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            disabledElevation = 0.dp,
            pressedElevation = 4.dp
        ),
        enabled = buttonEnabled()

    ) {
        Text(
            text = text, style = MaterialTheme.typography.bodyMedium
        )
        if (leadingIcon != null) {
            Spacer(modifier = modifier.width(4.dp))
            Icon(


                imageVector = leadingIcon, contentDescription = text,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(15.dp)
            )
        }

    }
}


@Composable
fun ATextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    buttonEnabled: () -> Boolean,
    leadingIcon: ImageVector? = null,
    enabledColor: Color = MaterialTheme.colorScheme.surface
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, bottom = 8.dp),
//            .clip(RoundedCornerShape(10.dp)),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        enabled = buttonEnabled(),
        colors = ButtonDefaults.buttonColors(contentColor = enabledColor),
        contentPadding = PaddingValues(2.dp)
    ) {
        Text(
            text = text, style = MaterialTheme.typography.bodyMedium
        )
        if (leadingIcon != null) {
            Spacer(modifier = modifier.width(4.dp))
            Icon(
                imageVector = leadingIcon, contentDescription = text,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(15.dp)
            )
        }

    }
}