package com.github.stephenwanjala.smartattend.auth.login.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(
    val access: String,
    val refresh: String,
    val user_id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val reg_number: String,
) : Parcelable

@Parcelize
data class  Token(
    val access: String,
    val refresh: String
) : Parcelable


@Parcelize
data class TokenData(
    val access: String,
    val refresh: String,
    val user_id: Int,
    val reg_number: String,
    val email: String,
    val first_name: String,
    val last_name: String
) : Parcelable

@Parcelize
data class LogoutRequest(
    val refresh_token: String,
) : Parcelable