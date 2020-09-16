package br.com.joaoov.ext

import android.widget.EditText

fun EditText.getString(): String {
    return try {
        this.text.toString()
    } catch (e: Exception) {
        ""
    }
}

fun EditText.getDouble(): Double {
    return try {
        this.text.toString().toDouble()
    } catch (e: Exception) {
        0.0
    }
}