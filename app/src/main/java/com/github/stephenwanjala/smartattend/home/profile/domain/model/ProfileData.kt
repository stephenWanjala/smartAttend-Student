package com.github.stephenwanjala.smartattend.home.profile.domain.model

data class ProfileData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val course: String,
    val enrolledUnits: List<String>,
    val regnumber: String
)