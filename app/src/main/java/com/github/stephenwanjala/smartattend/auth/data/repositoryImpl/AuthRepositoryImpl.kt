package com.github.stephenwanjala.smartattend.auth.data.repositoryImpl

import com.github.stephenwanjala.smartattend.auth.data.network.AuthApi
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthRequest
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.auth.login.domain.model.Token
import com.github.stephenwanjala.smartattend.auth.login.domain.model.TokenData
import com.github.stephenwanjala.smartattend.auth.login.domain.repository.AuthRepository
import com.github.stephenwanjala.smartattend.core.util.Resource
import com.github.stephenwanjala.smartattend.core.util.UiText
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val preferences: SmartAttendPreferences
) : AuthRepository {
    override suspend fun login(authRequest: AuthRequest): Flow<Resource<AuthResponse>> {
        return flow {
            emit(Resource.Loading())

            val response = authApi.login(authRequest)
            preferences.saveAToken(
                token = TokenData(
                    access = response.access,
                    refresh = response.refresh,
                    user_id = response.user_id,
                    reg_number = response.reg_number,
                    first_name = response.first_name,
                    last_name = response.last_name,
                    email = response.email

                )
            )
            emit(Resource.Success(response))
        }.catch { e ->
            emit(
                Resource.Error(
                    uiText = UiText.DynamicString(
                        e.localizedMessage ?: "Error Occurred"
                    )
                )
            )
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun logout(token: Token): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())

            authApi.logout(token.refresh, token.access)
            preferences.deleteToken()
            emit(Resource.Success(Unit))

        }.catch { e ->
            emit(
                Resource.Error(
                    uiText = UiText.DynamicString(
                        e.localizedMessage ?: "Error Occurred"
                    )
                )
            )
        }
    }

    override suspend fun tokenVerify(): Flow<Resource<TokenData>> {
        return flow<Resource<TokenData>> {
            emit(Resource.Loading())
            preferences.getToken().collectLatest { data ->
                data?.let { tokenData ->
                    tokenData.refresh.let { refreshToken ->
                        if (refreshToken.isNotBlank() && refreshToken.isNotEmpty()) {
                            val response = authApi.verify(refreshToken)
                            if (response.isSuccessful && response.code() == 200) {

                                emit(Resource.Success(data))
                            } else {
                                emit(
                                    Resource.Error(
                                        uiText = UiText.DynamicString(
                                            response.errorBody()?.string() ?: "Invalid Token"
                                        )
                                    )
                                )
                            }
                        }

                    }
                }
            }


        }.catch { e ->
            emit(
                Resource.Error(
                    uiText = UiText.DynamicString(
                        e.localizedMessage ?: "Error Occurred"
                    )
                )
            )
        }
    }

}