package br.com.joaoov.ext

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

private const val INVALID_EMAIL = "E-mail inválido"

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun TextInputLayout.setTypeEmail() {
    this.editText?.let {
        it.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                error = when {
                    this.getString().isValidEmail() -> {
                        null
                    }
                    else -> {
                        INVALID_EMAIL
                    }
                }
            }
        }
    }
}
