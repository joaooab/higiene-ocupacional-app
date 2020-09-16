package br.com.joaoov.ext

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(): String {
    return try {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        ""
    }
}