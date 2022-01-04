package com.fzanutto.apiconnection.model

import java.util.Date

data class Event(
    val title: String,
    val date: Date,
    val imageUrl: String,
    val longitude: Double,
    val latitude: Double,
    val id: Int,
    val description: String,
    val price: Double
)