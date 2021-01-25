package br.com.joaoov.ext

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

private const val INVALID_EMAIL = "E-mail inválido"

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun TextInputLayout.setTypeEmail() {
    this.errorListener()
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

fun TextInputLayout.errorListener() {
    editText?.addTextChangedListener(object :
        TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            error = null
        }
    })
}
