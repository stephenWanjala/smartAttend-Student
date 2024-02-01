package com.github.stephenwanjala.smartattend.auth.login.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(
    val token: Token
) : Parcelable

@Parcelize
data class Token(
    val access: String,
    val refresh: String
) : Parcelable