package com.github.stephenwanjala.smartattend.auth.login.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthRequest(
    val reg_number:String,
    val password:String
) : Parcelable
