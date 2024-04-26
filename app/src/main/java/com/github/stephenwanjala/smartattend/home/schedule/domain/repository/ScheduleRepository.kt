package com.github.stephenwanjala.smartattend.home.schedule.domain.repository

import com.github.stephenwanjala.smartattend.core.util.Resource
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.LectureScheduleItem
import com.github.stephenwanjala.smartattend.home.schedule.domain.model.TokenHeader
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getSchedules(tokenHeader: TokenHeader): Flow<Resource<List<LectureScheduleItem>>>

}