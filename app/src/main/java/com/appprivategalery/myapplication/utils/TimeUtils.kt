package com.appprivategalery.myapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getStringFromLocalTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        return time.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalTimeFromString(timeString:String) : LocalTime{
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
        return LocalTime.parse(timeString,formatter)
    }
}