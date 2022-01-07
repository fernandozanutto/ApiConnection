package com.fzanutto.apiconnection.view.details

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fzanutto.apiconnection.R
import com.fzanutto.apiconnection.databinding.ActivityEventDetailsBinding
import com.fzanutto.apiconnection.model.Event
import com.fzanutto.apiconnection.network.ApiConnectionImpl
import com.fzanutto.apiconnection.utils.SupportMapFragmentWrapper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class EventDetailsActivity : AppCompatActivity(), OnMapReadyCallback, RequestListener<Drawable> {

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

        val bundle = intent.extras
        (bundle?.getSerializable(EVENT_BUNDLE_KEY) as? Event)?.let {
            event = it
        } ?: run {
            finish()
        }

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

    private fun showCheckInModal() {
        val fragmentManager = supportFragmentManager

        val bottomSheet =
            BottomSheet(binding.root, event.id, ApiConnectionImpl(this@EventDetailsActivity))
        bottomSheet.show(fragmentManager, BottomSheet.TAG)
    }

    private fun showShareModal() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"

        var body = event.title

        event.date?.let { date ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            body += "\nData: ${dateFormat.format(date)}"
        }

        event.price?.let { price ->
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            body += "\nValor: ${numberFormat.format(price)}"
        }
        if (!event.longitude.isNaN() && !event.latitude.isNaN()) {
            body += "\nhttps://www.google.com/maps/@${event.latitude},${event.longitude},13z"
        }

        shareIntent.putExtra(Intent.EXTRA_TEXT, body)

        startActivity(Intent.createChooser(shareIntent, "Compartilhar"))
    }

    private fun setupViewData() {
        binding.apply {

            toolbar.setNavigationOnClickListener {
                finish()
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share_toolbar -> {
                        showShareModal()
                        true
                    }
                    R.id.check_in_toobar -> {
                        showCheckInModal()
                        true
                    }
                    else -> false
                }
            }


            title.text = event.title
            description.text = event.description

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
                    this@EventDetailsActivity.getString(R.string.free)
                }
            } ?: run {
                priceLayout.visibility = View.GONE
            }

            checkIn.setOnClickListener {
                showCheckInModal()
            }

            event.imageUrl?.let {
                if (it.isEmpty()) {
                    image.visibility = View.GONE
                    return
                }

                val circularProgressDrawable = CircularProgressDrawable(this@EventDetailsActivity)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                Glide.with(root)
                    .load(it)
                    .placeholder(circularProgressDrawable)
                    .fitCenter()
                    .timeout(3000)
                    .listener(this@EventDetailsActivity)
                    .into(image)
            } ?: run {
                image.visibility = View.GONE
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (event.latitude.isNaN() || event.longitude.isNaN()) {
            binding.map.visibility = View.GONE
            return
        }

        mMap.uiSettings.setAllGesturesEnabled(true)

        val point = LatLng(event.latitude, event.longitude)
        mMap.addMarker(MarkerOptions().position(point).title(event.title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13f))
    }

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
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        return false
    }
}