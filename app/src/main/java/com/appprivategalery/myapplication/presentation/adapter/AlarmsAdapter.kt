package com.appprivategalery.myapplication.presentation.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appprivategalery.myapplication.MainActivity
import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.databinding.AlarmListItemBinding
import com.appprivategalery.myapplication.presentation.viewmodel.alarm.AlarmsViewModel
import com.appprivategalery.myapplication.presentation.viewmodel.alarm.AlarmsViewModelFactory
import com.appprivategalery.myapplication.utils.AlarmUtils
import com.appprivategalery.myapplication.utils.TimeUtils
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmsAdapter : RecyclerView.Adapter<AlarmsAdapter.AlarmsViewHolder>() {

    lateinit var alarmsViewModel: AlarmsViewModel

    var alarmUtils = AlarmUtils

    private lateinit var context: Context

    private val callback = object : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmsViewHolder {
        val binding = AlarmListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return AlarmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmsViewHolder, position: Int) {
        val alarm = differ.currentList[position]
        holder.bind(alarm)

        //holder.binding.liSwAlarmSwitch.setOnCheckedChangeListener() DOESNT WORK IN THIS UTILITY

        holder.binding.alarmSwitch.setOnClickListener {
            alarm.isTurnedOn = holder.binding.alarmSwitch.isChecked
            alarmsViewModel.updateAlarm(alarm)

            if (alarm.isTurnedOn == true) {
                alarmUtils.createAlarmNotificationChannel(context!!)
                alarmUtils.scheduleAlarm(context!!, alarm!!)
            } else {
                alarmUtils.createAlarmNotificationChannel(context)
                alarmUtils.cancelAlarm(context!!, alarm)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val timeUtils = TimeUtils

    private var onItemClickListener: ((Alarm) -> Unit)? = null

    fun setOnItemClickListener(listener: (Alarm) -> Unit) {
        onItemClickListener = listener
    }

    inner class AlarmsViewHolder(val binding: AlarmListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm) {
            binding.alarmSwitch.isChecked = alarm.isTurnedOn!!
            binding.alarmRepeat.text = if (alarm.repeat!!) "Каждый день" else " Только раз"
            binding.alarmTime.text = timeUtils.getStringFromLocalTime(alarm.time!!)
            binding.alarmTitle.text = alarm.title!!



            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(alarm)
                }
            }
        }
    }
}
