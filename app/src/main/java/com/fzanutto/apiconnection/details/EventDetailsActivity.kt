package com.fzanutto.apiconnection.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fzanutto.apiconnection.R
import com.fzanutto.apiconnection.databinding.ActivityEventDetailsBinding
import com.fzanutto.apiconnection.model.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bundle = intent.extras
        (bundle?.getSerializable(EVENT_BUNDLE_KEY) as? Event)?.let {
            event = it
        } ?: run {
            finish()
        }

        setViewContent()
    }

    private fun setViewContent() {
        binding.apply {
            title.text = event.title
            description.text = event.description
            value.text = event.price.toString()

            val circularProgressDrawable = CircularProgressDrawable(this@EventDetailsActivity)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(binding.root)
                .load(event.imageUrl)
                .placeholder(circularProgressDrawable)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(binding.image)

        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val point = LatLng(event.latitude, event.longitude)
        mMap.addMarker(MarkerOptions().position(point).title(event.title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 12f))

    }
}