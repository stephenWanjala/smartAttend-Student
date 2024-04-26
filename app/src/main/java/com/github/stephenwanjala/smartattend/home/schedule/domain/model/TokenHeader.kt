package com.github.stephenwanjala.smartattend.home.schedule.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenHeader(
    val accessToken:String,
) : Parcelable
