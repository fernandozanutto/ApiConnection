package com.fzanutto.apiconnection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fzanutto.apiconnection.databinding.EventListBinding
import com.fzanutto.apiconnection.model.Event

class EventAdapter(private val eventList: List<Event>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: EventListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        holder.binding.text.text = event.title
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}