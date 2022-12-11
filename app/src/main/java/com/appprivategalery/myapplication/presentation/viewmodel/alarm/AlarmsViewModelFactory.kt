package com.appprivategalery.myapplication.presentation.viewmodel.alarm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appprivategalery.myapplication.domain.usecase.alarm.DeleteAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.SavedAlarmsUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.SaveAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.UpdateAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.event.DeleteEventUseCase

class AlarmsViewModelFactory (
    private val app:Application,
    private val saveAlarmUseCase: SaveAlarmUseCase,
    private val SavedAlarmsUseCase: SavedAlarmsUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
        ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmsViewModel(
            app,
            saveAlarmUseCase,
            SavedAlarmsUseCase,
            updateAlarmUseCase,
            deleteAlarmUseCase
        ) as T
    }
}