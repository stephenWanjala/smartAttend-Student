package com.github.stephenwanjala.smartattend.home.schedule.data.repositoryImpl

import com.github.stephenwanjala.smartattend.auth.data.network.AuthApi
import com.github.stephenwanjala.smartattend.core.util.Resource
import com.github.stephenwanjala.smartattend.core.util.UiText
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.LectureScheduleItem
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.TokenHeader
import com.github.stephenwanjala.smartattend.home.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : ScheduleRepository {
    override suspend fun getSchedules(tokenHeader: TokenHeader): Flow<Resource<List<LectureScheduleItem>>> {
        return flow<Resource<List<LectureScheduleItem>>> {
            emit(Resource.Loading())
            val response = authApi.getSchedules(
                accessToken ="Bearer ${tokenHeader.accessToken}"
            )
            if (response.isSuccessful) {
                println("The Response is $response")
                emit(Resource.Success(response.body() ?: emptyList()))
            } else {
                emit(Resource.Error(UiText.DynamicString(value = response.message())))
                println("The Error is ${response.message()}")
            }

        }.catch { e ->
            emit(Resource.Error(UiText.DynamicString(value = e.localizedMessage ?: "An error occurred")))
        }
    }
}