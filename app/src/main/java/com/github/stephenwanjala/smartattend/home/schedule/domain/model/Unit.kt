package com.github.stephenwanjala.smartattend.home.schedule.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Unit(
    val id: Int,
    val semester: Int,
    val unit_code: String,
    val unit_description: String,
    val unit_name: String
) : Parcelable