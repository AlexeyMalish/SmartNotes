package com.appprivategalery.myapplication.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.appprivategalery.myapplication.presentation.systemActivity.AlarmOnActivity
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.Alarm
import java.time.LocalTime
import java.util.*


const val alarmChannelID = "com.appprivategalery.myapplication.channelAlarm"
const val alarmTitleExtra = "com.appprivategalery.myapplication.titleExtraAlarm"
const val alarmMessageExtra = "com.appprivategalery.myapplication.messageExtraAlarm"
const val alarmID = "com.appprivategalery.myapplication.alarmID"
const val alarmCredentials = "alarmCredentials"

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val tapResultIntent = Intent(context, AlarmOnActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification: Notification = NotificationCompat.Builder(context, alarmChannelID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(intent.getStringExtra(alarmTitleExtra))
            .setContentText(intent.getStringExtra(alarmMessageExtra))
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(intent.getIntExtra(alarmID, 0), notification)

        var alarmUtils = AlarmUtils
        var mAlarm = intent.getSerializableExtra(alarmCredentials) as Alarm

        if (mAlarm.vibration!!) {
            alarmUtils.vibe = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            var pattern = longArrayOf(0, 200, 0)
            alarmUtils.vibe.vibrate(pattern, 0)
        } else {
            alarmUtils.vibe = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            var pattern = longArrayOf(0, 200, 0)
            alarmUtils.vibe.vibrate(pattern, 0)
        }

        alarmUtils.mediaPlayer.start()

    }
}

object AlarmUtils {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var vibe: Vibrator


    private var notificationManager: NotificationManager? = null

    fun createAlarmNotificationChannel(context: Context) {//id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(alarmChannelID, alarmTitleExtra, importance).apply {
                description = alarmMessageExtra
            }
            channel.setSound(null, null)
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun scheduleAlarm(context: Context, mAlarm: Alarm) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val title = context.getString(R.string.notification)
        val message = "Tap to turn off the alarm"

        intent.putExtra(alarmTitleExtra, title)
        intent.putExtra(alarmMessageExtra, message)
        intent.putExtra(alarmID, mAlarm.id)
        intent.putExtra(alarmCredentials, mAlarm)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            mAlarm.id!!,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(mAlarm.time!!)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC,
            time,
            pendingIntent
        )
    }

    private fun getTime(localTime: LocalTime): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, localTime.hour)
        calendar.set(Calendar.MINUTE, localTime.minute)
        calendar.set(Calendar.SECOND, 0)
        if (calendar.before(Calendar.getInstance()))
            calendar.add(Calendar.DAY_OF_MONTH, 1)

        return calendar.timeInMillis
    }

    fun cancelAlarm(context: Context, mAlarm: Alarm) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            mAlarm.id!!,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}

