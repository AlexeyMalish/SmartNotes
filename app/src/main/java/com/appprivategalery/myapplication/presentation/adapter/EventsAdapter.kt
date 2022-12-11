package com.appprivategalery.myapplication.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.databinding.EventListItemBinding
import com.appprivategalery.myapplication.utils.CalendarUtils
import com.appprivategalery.myapplication.utils.TimeUtils

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = EventListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = differ.currentList[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class EventsViewHolder(val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(event: Event) {

            binding.cfLiTvEventTitle.text = event.name
            binding.cfLiTvEventHour.text = event.eventTime?.let { it ->
                TimeUtils.getStringFromLocalTime(
                    it
                )
            }

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(event)
                }
            }
        }
    }

    private var onItemClickListener: ((Event) -> Unit)? = null

    fun setOnItemClickListener(listener: (Event) -> Unit) {
        onItemClickListener = listener
    }


}