package com.appprivategalery.myapplication.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.Event
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
const val notificationId = 1
const val channelID = "com.appprivategalery.myapplication.channel1"
const val titleExtra = "com.appprivategalery.myapplication.titleExtra"
const val messageExtra = "com.appprivategalery.myapplication.messageExtra"
const val eventID = "com.appprivategalery.myapplication.eventID"

class EventNotification : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val notification: Notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(intent.getIntExtra(eventID,0), notification)
    }
}

object EventNotificationUtils {
    private var notificationManager: NotificationManager? = null
    private  var calendarUtils = CalendarUtils
    private var timeUtils = TimeUtils

    fun createEventNotificationChannel(context: Context){//id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, titleExtra, importance).apply {
                description = messageExtra
            }
            notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }


    fun deleteNotification(context: Context,mEvent:Event){
        val intent = Intent(context, EventNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            mEvent.id!!,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

@RequiresApi(Build.VERSION_CODES.O)
fun scheduleNotification(context: Context, mEvent:Event) {
        val intent = Intent(context, EventNotification::class.java)
        val title = context.getString(R.string.notification)
        val message =
            if(mEvent.name.isNullOrBlank()) {context.getString(R.string.event)} else {mEvent?.name } + context.getString(R.string.planed) + calendarUtils.getStringFromLocalDate(
                mEvent.notificationDate!!) + context.getString(R.string.at) + timeUtils.getStringFromLocalTime(
                mEvent.notificationTime!!
            )
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        intent.putExtra(eventID,mEvent.id)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            mEvent.id!!,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(mEvent.notificationDate!!, mEvent.notificationTime!!)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC,
            time,
            pendingIntent
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime(localDate: LocalDate, localTime: LocalTime): Long {
        val calendar = Calendar.getInstance()
        //calendar.set(2021,12,1,23,10)
        calendar.set(
            localDate.year,
            localDate.monthValue - 1,
            localDate.dayOfMonth,
            localTime.hour,
            localTime.minute,
            1
        )

        return calendar.timeInMillis
    }


}