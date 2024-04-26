package com.github.stephenwanjala.smartattend.home.schedule.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LectureHall(
    val id: Int,
    val latitude: String,
    val longitude: String,
    val name: String
) : Parcelable