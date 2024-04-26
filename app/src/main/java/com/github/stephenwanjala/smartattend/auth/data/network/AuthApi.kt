package com.github.stephenwanjala.smartattend.auth.data.network

import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthRequest
import com.github.stephenwanjala.smartattend.auth.login.domain.model.AuthResponse
import com.github.stephenwanjala.smartattend.auth.login.domain.model.LogoutRequest
import com.github.stephenwanjala.smartattend.auth.login.domain.repository.AccessToken
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.LectureScheduleItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login/")
    suspend fun login(
        @Body loginRequest: AuthRequest
    ): AuthResponse

    @POST("auth/login/refresh/")
    suspend fun refreshToken(
        @Body refreshToken: String
    ): AccessToken

    @POST("auth/logout/")
    suspend fun logout(
        @Body logoutRequest: LogoutRequest,
        @Header("Authorization") accessToken: String
    ):Response<Unit>

    @POST("auth/login/verify/")
    suspend fun verify(
        @Body token: String
    ):Response<Unit>

    @GET("auth/student/schedules")
    suspend fun getSchedules(
        @Header("Authorization") accessToken: String
    ): Response<List<LectureScheduleItem>>

//    @GET("auth/student/student-attendance")

}