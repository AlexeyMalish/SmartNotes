package com.appprivategalery.myapplication.domain.usecase.alarm

import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.domain.repository.AlarmsRepository

class DeleteAlarmUseCase(private val alarmsRepository: AlarmsRepository) {
    suspend fun execute(alarm:Alarm) = alarmsRepository.deleteAlarm(alarm)
}