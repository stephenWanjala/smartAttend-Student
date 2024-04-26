package com.github.stephenwanjala.smartattend.home.schedule.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.LectureScheduleItem
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.format_Start_end_Time
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
        onDismissRequest = navigator::popBackStack,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 400.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = scheduleItem.unit.unit_name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = MaterialTheme.typography.headlineMedium.fontWeight
                )
                IconButton(onClick = navigator::popBackStack) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                }
            }
            Spacer(modifier = Modifier.heightIn(8.dp))
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(text = "Scheduled for ${scheduleItem.start_time.format_Start_end_Time()}  ~ ${scheduleItem.end_time.format_Start_end_Time()}")
                Text(text = "Lecturer : ${scheduleItem.lecturer.fullName}")
                Text(text = "Subject : ${scheduleItem.unit.unit_description}")
                Text(text = "Room : ${scheduleItem.lecture_hall.name}")

            }
        }
    }

}