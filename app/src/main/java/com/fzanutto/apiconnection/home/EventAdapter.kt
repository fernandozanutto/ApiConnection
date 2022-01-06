package com.fzanutto.apiconnection.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fzanutto.apiconnection.R
import com.fzanutto.apiconnection.databinding.EventListBinding
import com.fzanutto.apiconnection.details.EventDetailsActivity
import com.fzanutto.apiconnection.model.Event
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class EventAdapter(private var eventList: List<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    private val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun getItemCount(): Int = eventList.size

    fun updateEventList(list: List<Event>) {
        val oldSize = eventList.size
        eventList = list
        notifyItemRangeRemoved(0, oldSize)
        notifyItemRangeInserted(0, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        val context = holder.binding.root.context

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        holder.binding.apply {
            title.text = event.title
            date.text = dateFormat.format(event.date)
            price.text = numberFormat.format(event.price)

            Glide.with(root)
                .load(event.imageUrl)
                .placeholder(circularProgressDrawable)
                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(image)

            root.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable(EventDetailsActivity.EVENT_BUNDLE_KEY, event)
                }

                Intent(context, EventDetailsActivity::class.java).apply {
                    putExtras(bundle)
                    context.startActivity(this)
                }
            }
        }
    }

    inner class ViewHolder(val binding: EventListBinding) : RecyclerView.ViewHolder(binding.root)
}