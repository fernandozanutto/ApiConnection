package com.fzanutto.apiconnection.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fzanutto.apiconnection.model.CheckIn
import com.fzanutto.apiconnection.model.Event
import org.json.JSONObject
import java.util.Date

class ApiConnectionImpl(context: Context) : ApiConnection {

    private val volleyQueue = Volley.newRequestQueue(context)
    private val baseUrl = "http://5f5a8f24d44d640016169133.mockapi.io/api/"

    override fun getEventList(
        onSuccess: (events: List<Event>) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        val jsonRequest = JsonArrayRequest(
            baseUrl + "events",
            { response ->
                val events = arrayListOf<Event>()
                for (i in 0 until response.length()) {
                    val eventJson = response.getJSONObject(i)

                    val id = eventJson.optInt("id")
                    val title = eventJson.optString("title")
                    val description = eventJson.optString("description")
                    val imageUrl = eventJson.getString("image")

                    val lon = eventJson.optDouble("longitude")
                    val lat = eventJson.optDouble("latitude")

                    val date = eventJson.optLong("date", -1L).let {
                        if (it != -1L) Date(it) else null
                    }

                    val price = eventJson.optDouble("price", -1.0).let {
                        if (it != -1.0) it else null
                    }

                    events.add(
                        Event(
                            id = id,
                            title = title,
                            date = date,
                            imageUrl = imageUrl,
                            longitude = lon,
                            latitude = lat,
                            price = price,
                            description = description
                        )
                    )
                }

                onSuccess(events)
            },
            {
                if (it.networkResponse != null) {
                    onFailure(it.networkResponse.statusCode.toString())
                } else {
                    onFailure("Erro de rede")
                }
            })


        volleyQueue.add(jsonRequest)
    }

    override fun sendCheckIn(
        checkIn: CheckIn,
        onSuccess: () -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        val jsonObject = JSONObject()

        jsonObject.put("name", checkIn.name)
        jsonObject.put("email", checkIn.email)
        jsonObject.put("eventId", checkIn.eventId)

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            baseUrl + "checkin",
            jsonObject,
            {
                onSuccess()
            },
            {
                if (it.networkResponse != null) {
                    onFailure(it.networkResponse.statusCode.toString())
                } else {
                    onFailure("Erro de rede")
                }
            }
        )

        volleyQueue.add(jsonRequest)
    }
}