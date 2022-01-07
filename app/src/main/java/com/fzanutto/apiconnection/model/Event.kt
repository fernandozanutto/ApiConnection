package com.fzanutto.apiconnection.model

import java.io.Serializable
import java.util.Date

data class Event(
    val title: String,
    val date: Date?,
    val imageUrl: String? = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val id: Int = 0,
    val description: String = "",
    val price: Double? = 0.0
) : Serializable