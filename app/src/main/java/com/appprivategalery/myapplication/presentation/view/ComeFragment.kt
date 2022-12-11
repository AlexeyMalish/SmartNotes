package com.appprivategalery.myapplication.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appprivategalery.myapplication.MainActivity
import com.appprivategalery.myapplication.R
import com.appprivategalery.myapplication.databinding.FragmentComeBinding
import com.appprivategalery.myapplication.presentation.adapter.AlarmsAdapter
import com.appprivategalery.myapplication.presentation.adapter.EventsAdapter
import com.appprivategalery.myapplication.presentation.viewmodel.alarm.AlarmsViewModel
import com.appprivategalery.myapplication.presentation.viewmodel.event.EventsViewModel
import com.appprivategalery.myapplication.utils.AlarmUtils
import java.time.LocalDate

class ComeFragment : Fragment() {

    private lateinit var fragmentComeBinding: FragmentComeBinding

    private lateinit var comeEventsViewModel: EventsViewModel

    private lateinit var comeEventsAdapter: EventsAdapter

    private lateinit var alarmsAdapter: AlarmsAdapter

    private lateinit var alarmsViewModel: AlarmsViewModel

    private var alarmUtils = AlarmUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_come, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fragmentComeBinding = FragmentComeBinding.bind(view)
        comeEventsViewModel = (activity as MainActivity).comeEventsViewModel
        comeEventsAdapter = (activity as MainActivity).comeEventsAdapter

        alarmsAdapter = (activity as MainActivity).alarmsAdapter
        alarmsViewModel = (activity as MainActivity).alarmsViewModel


        initForthcomingRecyclerView()
        initAlarmsRecyclerView()

        setAlarmListItemTouchHelper()

        alarmsAdapter.setOnItemClickListener {
            Bundle().apply {
                putSerializable("selected_alarm", it)
                findNavController().navigate(R.id.action_forthcomingFragment_to_alarmFragment, this)
            }
        }

        fragmentComeBinding.ffIbAddAlarm.setOnClickListener {
            findNavController().navigate(R.id.action_forthcomingFragment_to_alarmFragment)
        }
    }

    private fun initAlarmsRecyclerView() {
        fragmentComeBinding.ffRvAlarms.apply {
            adapter = alarmsAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }
        alarmsViewModel.savedAlarms().observe(viewLifecycleOwner) {
            alarmsAdapter.differ.submitList(it)
        }
    }

    private fun initForthcomingRecyclerView() {
        fragmentComeBinding.ffRvComeEvents.apply {
            adapter = comeEventsAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }

        comeEventsViewModel.getEventsAtCertainWeek(LocalDate.now())
            .observe(viewLifecycleOwner) {
                comeEventsAdapter.differ.submitList(it)
            }
    }

    private fun setAlarmListItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val alarm = alarmsAdapter.differ.currentList[viewHolder.adapterPosition]
                alarmsViewModel.deleteAlarm(alarm)
                alarmUtils.createAlarmNotificationChannel(context!!)
                alarmUtils.cancelAlarm(context!!, alarm)
            }


        }).attachToRecyclerView(fragmentComeBinding.ffRvAlarms)
    }
}