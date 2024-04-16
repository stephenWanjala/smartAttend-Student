package com.github.stephenwanjala.smartattend.preferences.domain

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.stephenwanjala.smartattend.auth.login.domain.model.TokenData
import kotlinx.coroutines.flow.Flow

interface SmartAttendPreferences {
    fun getToken(): Flow<TokenData?>
    suspend fun saveAToken(token:TokenData)
    suspend fun deleteToken()

    companion object{
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val REG_NUMBER = stringPreferencesKey("reg_number")
        val USER_ID = intPreferencesKey("user_id")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL = stringPreferencesKey("email")
        const val SMART_ATTEND_PREFERENCES_NAME = "smart_attend_preferences"
    }
}