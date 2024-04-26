package com.github.stephenwanjala.smartattend.home.schedule.domain.model

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Parcelize
data class LectureScheduleItem(
    val attendance_tracking_started: Boolean,
    val date: String,
    val end_time: String,
    val id: Int,
    val lecture_hall: LectureHall,
    val lecturer: Lecturer,
    val start_time: String,
    val unit: Unit
) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: String): LocalDateTime {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return LocalDateTime.parse(time, inputFormat)
    }

    val startTimeFormatted: LocalDateTime
        @RequiresApi(Build.VERSION_CODES.O)
        get() = formatTime(start_time)

    val endTimeFormatted: LocalDateTime
        @RequiresApi(Build.VERSION_CODES.O)
        get() = formatTime(end_time)

    val status: String
//        Upcoming,Ongoing,Completed,Missed
    /*
    * 1. Upcoming - if the current time is less than the start time of the lecture
    * 2. Ongoing - if the current time is between the start time and end time of the lecture && attendance_tracking_started is true
    * 3. Completed - if the current time is greater than the end time of the lecture && attendance_tracking_started is true
    * 4. Missed - if the current time is greater than the end time of the lecture && attendance_tracking_started is false
     */
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val currentTime = LocalDateTime.now()
            return when {
                currentTime.isBefore(startTimeFormatted) -> "Upcoming"
                currentTime.isAfter(startTimeFormatted) && currentTime.isBefore(endTimeFormatted) && attendance_tracking_started -> "Ongoing"
                currentTime.isAfter(endTimeFormatted) && attendance_tracking_started -> "Completed"
                currentTime.isAfter(endTimeFormatted) && !attendance_tracking_started -> "Missed"
                else -> "Unknown"
            }
        }
 val statusColor: StatusColor
    @Composable
    @RequiresApi(Build.VERSION_CODES.O)
    get() {
        return when (status) {
            "Upcoming" -> StatusColor(Color(0xFFFFC107), Color.Black) // Yellowish color
            "Ongoing" -> StatusColor(Color(0xFF4CAF50), Color.White) // Same color as completed
            "Completed" -> StatusColor(Color(0xFF4CAF50), Color.White) // Success color (Green)
            "Missed" -> StatusColor(Color(0xFFF44336),MaterialTheme.colorScheme.onError) // Redish color
            else -> StatusColor(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.onBackground)
        }
    }


    inner class StatusColor(
        val container: Color,
        val contentColor: Color
    )


}


fun String.format_Start_end_Time(): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(this)
    val outputFormat = SimpleDateFormat("dd MMMM hh:mm a", Locale.getDefault())
    return outputFormat.format(date)

}

