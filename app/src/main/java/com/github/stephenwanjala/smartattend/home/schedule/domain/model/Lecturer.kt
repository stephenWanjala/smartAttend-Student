package com.github.stephenwanjala.smartattend.home.schedule.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lecturer(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val pf_number: String,
    val phone_number: String
) : Parcelable{
    val fullName: String
        get() = "$first_name $last_name"
}