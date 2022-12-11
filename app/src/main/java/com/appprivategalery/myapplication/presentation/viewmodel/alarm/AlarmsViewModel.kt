package com.appprivategalery.myapplication.presentation.viewmodel.alarm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.domain.usecase.alarm.DeleteAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.SavedAlarmsUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.SaveAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.UpdateAlarmUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlarmsViewModel(
    private val app:Application,
    private val saveAlarmUseCase: SaveAlarmUseCase,
    private val SavedAlarmsUseCase: SavedAlarmsUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : AndroidViewModel(app){

    suspend fun saveAlarm(alarm:Alarm) : Long = saveAlarmUseCase.execute(alarm)

    fun savedAlarms() = liveData {
        SavedAlarmsUseCase.execute().collect {
            emit(it)
        }
    }

    fun updateAlarm(alarm: Alarm) = viewModelScope.launch {
        updateAlarmUseCase.execute(alarm)
    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteAlarmUseCase.execute(alarm)
    }
}