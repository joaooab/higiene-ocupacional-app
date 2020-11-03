package br.com.joaoov.ext

import android.view.View
import com.google.android.material.textfield.TextInputLayout

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun TextInputLayout.getString(): String {
    return try {
        this.editText?.text.toString()
    } catch (e: Exception) {
        ""
    }
}

fun TextInputLayout.setString(text: String) = this.editText?.setText(text)

fun TextInputLayout.getDouble(): Double {
    return try {
        this.editText?.text.toString().toDouble()
    } catch (e: Exception) {
        0.0
    }
}