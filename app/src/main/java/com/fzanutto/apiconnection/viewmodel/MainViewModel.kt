package com.fzanutto.apiconnection.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fzanutto.apiconnection.model.Event
import com.fzanutto.apiconnection.network.ApiConnection

class MainViewModel(app: Application, private val api: ApiConnection) : AndroidViewModel(app) {

    val eventList = MutableLiveData<List<Event>>()

    fun callEventList() {
        api.getEventList({
            eventList.postValue(it)
        }, {

        })
    }

}