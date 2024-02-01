package com.github.stephenwanjala.smartattend.auth.data.repositoryImpl

import com.github.stephenwanjala.smartattend.auth.data.network.AuthApi
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthRequest
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.auth.login.domain.model.Token
import com.github.stephenwanjala.smartattend.auth.login.domain.repository.AccessToken
import com.github.stephenwanjala.smartattend.auth.login.domain.repository.AuthRepository
import com.github.stephenwanjala.smartattend.core.util.Resource
import com.github.stephenwanjala.smartattend.core.util.UiText
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val preferences: SmartAttendPreferences
) : AuthRepository {
    override suspend fun login(authRequest: AuthRequest): Flow<Resource<AuthResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = authApi.login(authRequest)
                preferences.saveAToken(response.token)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        uiText = UiText.DynamicString(
                            e.localizedMessage ?: "Error Occurred"
                        )
                    ))
            }
        }
    }

    override suspend fun logout(token: Token): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            try {
                authApi.logout(token.refresh, token.access)
                preferences.deleteToken()
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        uiText = UiText.DynamicString(
                            e.localizedMessage ?: "Error Occurred"
                        )
                    ))
            }
        }.catch { e ->
            emit(
                Resource.Error(
                    uiText = UiText.DynamicString(
                        e.localizedMessage ?: "Error Occurred"
                    )
                ))}
    }

    override suspend fun tokenVerify(refreshToken: String): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            try {
                authApi.verify(refreshToken)
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        uiText = UiText.DynamicString(
                            e.localizedMessage ?: "Error Occurred"
                        )
                    ))
            }
        }.catch { e ->
            emit(
                Resource.Error(
                    uiText = UiText.DynamicString(
                        e.localizedMessage ?: "Error Occurred"
                    )
                ))}
    }

    override suspend fun refreshToken(refreshToken: String): Flow<Resource<AccessToken>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = authApi.refreshToken(refreshToken)
                preferences.deleteToken()
                preferences.saveAToken(Token(access = response, refresh = refreshToken))
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        uiText = UiText.DynamicString(
                            e.localizedMessage ?: "Error Occurred"
                        )
                    ))
            }
        }.catch { e ->
            emit(
                Resource.Error(
                    uiText = UiText.DynamicString(
                        e.localizedMessage ?: "Error Occurred"
                    )
                ))}
    }
}