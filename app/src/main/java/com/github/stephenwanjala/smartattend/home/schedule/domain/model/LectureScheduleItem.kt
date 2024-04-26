package com.github.stephenwanjala.smartattend.home.schedule.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
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
) : Parcelable

fun  String.format_Start_end_Time():String{

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(this)
    val outputFormat =SimpleDateFormat("dd MMMM hh:mm a", Locale.getDefault())
    return outputFormat.format(date)

}

