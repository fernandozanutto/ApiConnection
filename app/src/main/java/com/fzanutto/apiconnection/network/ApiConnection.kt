package com.fzanutto.apiconnection.network

import com.fzanutto.apiconnection.model.CheckIn
import com.fzanutto.apiconnection.model.Event

interface ApiConnection {
    fun getEventList(onSuccess: (events: List<Event>) -> Unit,  onFailure: (error: String) -> Unit)
    fun sendCheckIn(checkIn: CheckIn, onSuccess: () -> Unit,  onFailure: (error: String) -> Unit)
}