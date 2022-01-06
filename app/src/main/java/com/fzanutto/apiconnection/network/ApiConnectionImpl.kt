package com.fzanutto.apiconnection.network

import android.content.Context
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.fzanutto.apiconnection.model.CheckIn
import com.fzanutto.apiconnection.model.Event
import java.util.Date

class ApiConnectionImpl(context: Context): ApiConnection {

    private val volleyQueue = Volley.newRequestQueue(context)
    private val baseUrl = "http://5f5a8f24d44d640016169133.mockapi.io/api/"

    override fun getEventList(onSuccess: (events: List<Event>) -> Unit, onFailure: () -> Unit) {
        val jsonRequest = JsonArrayRequest(
            baseUrl + "events",
            { response ->
                val events = arrayListOf<Event>()
                for (i in 0 until response.length()) {
                    val eventJson = response.getJSONObject(i)
                    events.add(
                        Event(
                            title = eventJson.getString("title"),
                            date = Date(eventJson.getLong("date")),
                            id = eventJson.getInt("id"),
                            imageUrl = eventJson.getString("image"),
                            longitude = eventJson.getDouble("longitude"),
                            latitude = eventJson.getDouble("latitude"),
                            price = eventJson.getDouble("price"),
                            description = eventJson.getString("description")
                        )
                    )
                }

                onSuccess(events)
            },
            {  })


        volleyQueue.add(jsonRequest)
    }

    override fun getEventById(id: Int, onSuccess: (event: Event) -> Unit, onFailure: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendCheckIn(checkIn: CheckIn, onSuccess: () -> Unit, onFailure: () -> Unit) {
        TODO("Not yet implemented")
    }
}