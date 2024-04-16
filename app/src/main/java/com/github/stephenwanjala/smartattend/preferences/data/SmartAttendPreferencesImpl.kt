package com.github.stephenwanjala.smartattend.preferences.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

import com.github.stephenwanjala.smartattend.auth.login.domain.model.TokenData
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.ACCESS_TOKEN
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.EMAIL
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.FIRST_NAME
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.LAST_NAME
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.REFRESH_TOKEN
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.REG_NUMBER
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.USER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SmartAttendPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
) : SmartAttendPreferences {

    override fun getToken(): Flow<TokenData?> {
        return dataStore.data.map { preferences: Preferences ->
            TokenData(
                access = preferences[ACCESS_TOKEN] ?: "",
                refresh = preferences[REFRESH_TOKEN] ?: "",
                user_id = preferences[USER_ID] ?: 0,
                reg_number = preferences[REG_NUMBER] ?: "",
                first_name = preferences[FIRST_NAME] ?: "",
                last_name = preferences[LAST_NAME] ?: "",
                email = preferences[EMAIL] ?: ""
            )
        }
    }

    override suspend fun saveAToken(tokenData: TokenData) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = tokenData.access
            preferences[REFRESH_TOKEN] = tokenData.refresh
            preferences[REG_NUMBER] = tokenData.reg_number
            preferences[USER_ID] = tokenData.user_id
            preferences[FIRST_NAME] = tokenData.first_name
            preferences[LAST_NAME] = tokenData.last_name
            preferences[EMAIL] = tokenData.email
        }
    }

    override suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
            preferences.remove(REG_NUMBER)
            preferences.remove(USER_ID)
            preferences.remove(FIRST_NAME)
            preferences.remove(LAST_NAME)
            preferences.remove(EMAIL)
        }
    }
}
