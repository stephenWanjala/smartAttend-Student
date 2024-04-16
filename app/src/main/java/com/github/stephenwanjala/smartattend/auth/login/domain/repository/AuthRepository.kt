package com.github.stephenwanjala.smartattend.auth.login.domain.repository

import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthRequest
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.auth.login.domain.model.Token
import com.github.stephenwanjala.smartattend.auth.login.domain.model.TokenData
import com.github.stephenwanjala.smartattend.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(authRequest: AuthRequest): Flow<Resource<AuthResponse>>

    suspend fun logout(token: Token): Flow<Resource<Unit>>
    suspend fun tokenVerify(): Flow<Resource<TokenData>>
//    suspend fun refreshToken(refreshToken: String): Flow<Resource<AccessToken>>

}

typealias AccessToken = String