package com.github.stephenwanjala.smartattend.home.schedule.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.LectureScheduleItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Composable
@Destination(style = DestinationStyle.Dialog::class)
fun SingleScheduleDialog(
    scheduleItem: LectureScheduleItem,
    navigator: DestinationsNavigator
) {
    Dialog(
        onDismissRequest = navigator::popBackStack ,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

        }
    }

}