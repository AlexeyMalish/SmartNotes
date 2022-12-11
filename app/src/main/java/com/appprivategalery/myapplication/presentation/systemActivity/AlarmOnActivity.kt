package com.appprivategalery.myapplication.presentation.systemActivity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appprivategalery.myapplication.databinding.ActivityAlarmOnBinding
import com.appprivategalery.myapplication.databinding.ActivityMainBinding
import com.appprivategalery.myapplication.utils.AlarmUtils


class AlarmOnActivity : AppCompatActivity() {

    private var alarmUtils = AlarmUtils

    private lateinit var binding: ActivityAlarmOnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmOnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.turnOff.setOnClickListener {
            alarmUtils.mediaPlayer.stop()
            alarmUtils.vibe.cancel()
            onBackPressed()
        }
    }
}