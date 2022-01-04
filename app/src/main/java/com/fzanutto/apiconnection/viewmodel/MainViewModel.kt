package com.fzanutto.apiconnection.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fzanutto.apiconnection.model.Event

class MainViewModel(app: Application) : AndroidViewModel(app) {

    val eventList = arrayListOf<Event>()
}