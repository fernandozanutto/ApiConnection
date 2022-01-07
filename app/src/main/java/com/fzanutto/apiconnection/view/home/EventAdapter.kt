package com.fzanutto.apiconnection.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fzanutto.apiconnection.R
import com.fzanutto.apiconnection.databinding.EventListBinding
import com.fzanutto.apiconnection.view.details.EventDetailsActivity
import com.fzanutto.apiconnection.model.Event
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private var eventList: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateEventList(list: List<Event>) {
        val size = eventList.size
        eventList.clear()
        notifyItemRangeRemoved(0, size)
        eventList.addAll(list)
        notifyItemRangeInserted(0, eventList.size)
    }

    inner class ViewHolder(val binding: EventListBinding, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onItemClicked(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) { position ->
            val event = eventList[position]
            val bundle = Bundle().apply {
                putSerializable(EventDetailsActivity.EVENT_BUNDLE_KEY, event)
            }
            val intent = Intent(parent.context, EventDetailsActivity::class.java).apply {
                putExtras(bundle)
            }

            parent.context.startActivity(intent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        val context = holder.binding.root.context

        holder.binding.apply {
            title.text = event.title

            event.date?.let {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                date.text = dateFormat.format(it)
            } ?: run {
                dateLayout.visibility = View.GONE
            }

            event.price?.let {
                price.text = if (it != 0.0) {
                    val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                     numberFormat.format(it)
                } else {
                    context.getString(R.string.free)
                }
            } ?: run {
                priceLayout.visibility = View.GONE
            }

            event.imageUrl?.let {
                if (it.isEmpty()) {
                    image.visibility = View.GONE
                    return
                }

                val circularProgressDrawable = CircularProgressDrawable(context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                Glide.with(root)
                    .load(it)
                    .placeholder(circularProgressDrawable)
                    .centerCrop()
                    .timeout(3000)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(image)
            } ?: run {
                image.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = eventList.size
}