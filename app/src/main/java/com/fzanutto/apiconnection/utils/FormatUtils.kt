package com.fzanutto.apiconnection.utils

import android.content.Context
import com.fzanutto.apiconnection.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object FormatUtils {

    fun formatDate(format: String, date: Date): String {
        val dateFormat = SimpleDateFormat(format, Locale.ROOT)
        return dateFormat.format(date)
    }

    fun formatCurrency(context: Context, price: Double) : String {
        return if (price != 0.0) {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            numberFormat.format(price)
        } else {
            context.getString(R.string.free)
        }
    }
}