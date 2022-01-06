package com.fzanutto.apiconnection.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.fzanutto.apiconnection.R
import com.google.android.gms.maps.SupportMapFragment

class SupportMapFragmentWrapper: SupportMapFragment() {

    lateinit var listener: OnTouchListener

    interface OnTouchListener {
        abstract fun onTouch()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = super.onCreateView(inflater, container, savedInstanceState)

        context?.let {
            val frameLayout = TouchableWrapper(it)

            frameLayout.setBackgroundColor(ContextCompat.getColor(it, R.color.transparent))

            (layout as ViewGroup).addView(frameLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }

        return layout
    }

    inner class TouchableWrapper(context: Context): FrameLayout(context) {

        override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
            when (ev?.action){
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_UP -> {
                    listener.onTouch()
                }
            }

            return super.dispatchTouchEvent(ev)
        }

    }
}