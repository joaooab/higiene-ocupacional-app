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

fun Int.toHour(): String {
    return if (this < 10) {
        "0$this"
    } else {
        "$this"
    }
}

fun Int.toMinute(): String {
    return if (this < 10) {
        "${this}0"
    } else {
        "$this"
    }
}