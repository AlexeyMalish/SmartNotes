package com.appprivategalery.myapplication.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.appprivategalery.myapplication.MainActivity
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.databinding.FragmentAlarmBinding
import com.appprivategalery.myapplication.presentation.viewmodel.alarm.AlarmsViewModel
import com.appprivategalery.myapplication.utils.AlarmUtils
import kotlinx.coroutines.launch
import java.time.LocalTime

class AlarmFragment : Fragment() {
    private lateinit var fragmentAlarmBinding: FragmentAlarmBinding

    private lateinit var alarmsViewModel: AlarmsViewModel

    private var mAlarm: Alarm? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentAlarmBinding = FragmentAlarmBinding.bind(view)
        fragmentAlarmBinding.afTimePicker.setIs24HourView(true)

        alarmsViewModel = (activity as MainActivity).alarmsViewModel

        val args: AlarmFragmentArgs by navArgs()
        mAlarm = args.selectedAlarm

        if (mAlarm == null)
            mAlarm = Alarm(
                null,
                null,
                LocalTime.of(
                    fragmentAlarmBinding.afTimePicker.hour,
                    fragmentAlarmBinding.afTimePicker.minute
                ),
                repeat = false,
                vibration = false,
                isTurnedOn = true
            )
        else {
            fragmentAlarmBinding.afEtTitle.setText(mAlarm?.title)
            fragmentAlarmBinding.afCvAlarmRepeat.isChecked = mAlarm!!.repeat!!
            fragmentAlarmBinding.afCvAlarmVibration.isChecked = mAlarm!!.vibration!!

        }

        fragmentAlarmBinding.afIbCancel.setOnClickListener {
            activity?.onBackPressed()
        }

        fragmentAlarmBinding.afIbConfirm.setOnClickListener {
            mAlarm?.title = fragmentAlarmBinding.afEtTitle.text.toString()
            mAlarm?.repeat = fragmentAlarmBinding.afCvAlarmRepeat.isChecked
            mAlarm?.vibration = fragmentAlarmBinding.afCvAlarmVibration.isChecked
            mAlarm?.time = LocalTime.of(
                fragmentAlarmBinding.afTimePicker.hour,
                fragmentAlarmBinding.afTimePicker.minute
            )

            if (mAlarm!!.id == null) {
                var alarmId: Long? = null
                viewLifecycleOwner.lifecycleScope.launch {
                    alarmId = alarmsViewModel.saveAlarm(mAlarm!!)
                }.invokeOnCompletion {
                    mAlarm!!.id = alarmId!!.toInt()
                    var alarmUtils = AlarmUtils
                    alarmUtils.createAlarmNotificationChannel(context!!)
                    alarmUtils.scheduleAlarm(context!!, mAlarm!!)

                    activity?.onBackPressed()

                }
            } else {
                alarmsViewModel.updateAlarm(mAlarm!!)
                var alarmUtils = AlarmUtils
                alarmUtils.createAlarmNotificationChannel(context!!)
                alarmUtils.cancelAlarm(context!!, mAlarm!!)
                alarmUtils.scheduleAlarm(context!!, mAlarm!!)
                activity?.onBackPressed()
            }
        }
    }

}
