package com.github.stephenwanjala.smartattend.home.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.stephenwanjala.smartattend.home.profile.domain.model.ProfileData

@Composable
fun StudentProfileData(
    modifier: Modifier = Modifier,
    profileData: ProfileData
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileDataItem(label = "First Name", value = profileData.firstName)
        ProfileDataItem(label = "Last Name", value = profileData.lastName)
        ProfileDataItem(label = "Email", value = profileData.email)
        ProfileDataItem(label = "Phone Number", value = profileData.phoneNumber)
        ProfileDataItem(label = "Reg Number", value = profileData.regnumber)
        ProfileDataItem(label = "Course", value = profileData.course)
        MyUnits(units = profileData.enrolledUnits)
    }
}