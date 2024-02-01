package com.github.stephenwanjala.smartattend.preferences.domain

import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.stephenwanjala.smartattend.auth.login.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface SmartAttendPreferences {
    fun getAccessToken(): Flow<String?>
    fun getRefreshToken(): Flow<String?>
    fun getToken(): Flow<Token?>
    suspend fun saveAToken(token:Token)
    suspend fun deleteToken()

    companion object{
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}