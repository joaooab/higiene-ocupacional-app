package br.com.joaoov.ext

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

private const val MIN_PASSWORD_LENGHT = 6

const val FIELD_ERROR_REQUIRED = "Campo obrigatório"
const val FIELD_ERROR_EMAIL = "E-mail inválido"
const val FIELD_ERROR_PASSWORD = "Senha deve conter no mínimo $MIN_PASSWORD_LENGHT caracteres"
const val FIELD_ERROR_PASSWORD_CONFIRM = "As senhas não conferem"

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= MIN_PASSWORD_LENGHT
}

fun TextInputLayout.setTypeRequired() {
    this.errorListener()
    this.editText?.let {
        it.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                error = if (it.text.toString().isNotEmpty()) {
                    null
                } else {
                    FIELD_ERROR_REQUIRED
                }
            }
        }
    }
}


fun TextInputLayout.setTypeEmail(isRequiered: Boolean) {
    this.errorListener()
    this.editText?.let {
        it.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                error = when {
                    !isRequiered && this.getString().isEmpty() -> {
                        null
                    }
                    this.getString().isValidEmail() -> {
                        null
                    }
                    else -> {
                        FIELD_ERROR_EMAIL
                    }
                }
            }
        }
    }
}

fun TextInputLayout.setTypePassword() {
    this.errorListener()
    this.editText?.let {
        it.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                error = when {
                    this.getString().isValidPassword() -> {
                        null
                    }
                    else -> {
                        FIELD_ERROR_PASSWORD
                    }
                }
            }
        }
    }
}

fun TextInputLayout.setTypePasswordConfirm(passwordField: TextInputLayout) {
    this.errorListener()
    this.editText?.let {
        it.setOnFocusChangeListener { _, hasFocus ->
            val passwordConfirm = this.getString()
            if (!hasFocus) {
                error = when {
                    passwordConfirm.isValidPassword() && passwordConfirm == passwordField.getString() -> {
                        null
                    }
                    else -> {
                        FIELD_ERROR_PASSWORD_CONFIRM
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
