package com.fzanutto.apiconnection.view.details

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fzanutto.apiconnection.R
import com.fzanutto.apiconnection.databinding.ActivityEventDetailsBinding
import com.fzanutto.apiconnection.model.Event
import com.fzanutto.apiconnection.utils.SupportMapFragmentWrapper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class EventDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityEventDetailsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var event: Event

    companion object {
        const val EVENT_BUNDLE_KEY = "EVENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            elevation = 0f
        }


        val bundle = intent.extras
        (bundle?.getSerializable(EVENT_BUNDLE_KEY) as? Event)?.let {
            event = it
        } ?: finish()

        setupMap()
        setupViewData()
    }

    private fun setupMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragmentWrapper
        mapFragment.listener = object : SupportMapFragmentWrapper.OnTouchListener {
            override fun onTouch() {
                binding.scrollView.requestDisallowInterceptTouchEvent(true)
            }
        }
        mapFragment.getMapAsync(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewData() {
        binding.apply {
            title.text = event.title
            description.text = event.description

            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ROOT)
            date.text = dateFormat.format(event.date)

            val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            price.text = numberFormat.format(event.price)

            checkIn.setOnClickListener {
                Toast.makeText(this@EventDetailsActivity, "aaaaa", Toast.LENGTH_SHORT).show()
            }

            val circularProgressDrawable = CircularProgressDrawable(this@EventDetailsActivity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(binding.root)
                .load(event.imageUrl)
                .placeholder(circularProgressDrawable)
                .fitCenter()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.image.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(binding.image)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.setAllGesturesEnabled(true)

        val point = LatLng(event.latitude, event.longitude)
        mMap.addMarker(MarkerOptions().position(point).title(event.title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13f))
    }
}