package com.appprivategalery.myapplication.domain.usecase.alarm

import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.domain.repository.AlarmsRepository
import kotlinx.coroutines.flow.Flow

class SavedAlarmsUseCase(private val alarmsRepository: AlarmsRepository) {
    fun execute() : Flow<List<Alarm>> = alarmsRepository.savedAlarms()
}