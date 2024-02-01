package com.github.stephenwanjala.smartattend.preferences.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.github.stephenwanjala.smartattend.auth.login.domain.model.Token
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.ACCESS_TOKEN
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.REFRESH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SmartAttendPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
):SmartAttendPreferences {
    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    override fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }
    }

    override fun getToken(): Flow<Token?> {
        return dataStore.data.map { preferences: Preferences ->
            Token(
                access = preferences[ACCESS_TOKEN] ?: "",
                refresh = preferences[REFRESH_TOKEN] ?: ""
            )
        }
    }

    override suspend fun saveAToken(token: Token) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token.access
            preferences[REFRESH_TOKEN] = token.refresh
        }
    }

    override suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
        }
    }
}
