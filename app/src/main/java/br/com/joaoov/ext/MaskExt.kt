package br.com.joaoov.ext

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import java.util.regex.Pattern

private const val MIN_PASSWORD_LENGHT = 6

const val FIELD_ERROR_REQUIRED = "Campo obrigatório"
const val FIELD_ERROR_CNPJ = "CNPJ inválido"
const val FIELD_ERROR_EMAIL = "E-mail inválido"
const val FIELD_ERROR_PASSWORD = "Senha deve conter no mínimo $MIN_PASSWORD_LENGHT caracteres"
const val FIELD_ERROR_PASSWORD_CONFIRM = "As senhas não conferem"

const val MASK_CPF = "###.###.###-##"
const val MASK_CNPJ = "##.###.###/####-##"
const val MASK_MOBILE_NUMBER = "(##) # ####-####"
const val MASK_PHONE_NUMBER = "(##) ####-####"
const val MASK_ZIPCODE = "#####-###"
const val MASK_DATE = "##/##/####"

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= MIN_PASSWORD_LENGHT
}

fun String.isValidCNPJ(): Boolean {
    val cnpj = this.getOnlyNumbers()
    if (cnpj == "00000000000000" ||
        cnpj == "11111111111111" ||
        cnpj == "22222222222222" ||
        cnpj == "33333333333333" ||
        cnpj == "44444444444444" ||
        cnpj == "55555555555555" ||
        cnpj == "66666666666666" ||
        cnpj == "77777777777777" ||
        cnpj == "88888888888888" ||
        cnpj == "99999999999999" ||
        cnpj.length != 14
    ) return false
    val dig13: Char
    val dig14: Char
    var sm: Int
    var i: Int
    var r: Int
    var num: Int
    var peso: Int
    return try {
        sm = 0
        peso = 2
        i = 11
        while (i >= 0) {
            num = (cnpj[i].toInt() - 48)
            sm += num * peso
            peso += 1
            if (peso == 10) peso = 2
            i--
        }
        r = sm % 11
        dig13 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
        sm = 0
        peso = 2
        i = 12
        while (i >= 0) {
            num = (cnpj[i].toInt() - 48)
            sm += num * peso
            peso += 1
            if (peso == 10) peso = 2
            i--
        }
        r = sm % 11
        dig14 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
        dig13 == cnpj[12] && dig14 == cnpj[13]
    } catch (erro: InputMismatchException) {
        false
    }
}

fun String.mask(format: String): String {
    if (this.containsMask(format)) return this
    var maskedText = ""
    var i = 0
    for (m in format.toCharArray()) {
        if (m != '#') {
            maskedText += m
            continue
        }
        try {
            maskedText += this[i]
        } catch (e: Exception) {
            break
        }
        i++
    }

    return maskedText
}

fun String.containsMask(format: String): Boolean {
    val regex = format.replace("#", "\\d")
        .replace(".", "\\.")
        .replace("(", "\\(")
        .replace(")", "\\)")

    return Pattern.compile(regex).matcher(this).matches()
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

fun TextInputLayout.setTypeCNPJ(isRequiered: Boolean) {
    this.mask(MASK_CNPJ)
    this.editText?.let {
        it.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                error = when {
                    !isRequiered && this.getString().isEmpty() -> {
                        null
                    }
                    this.getString().isValidCNPJ() -> {
                        null
                    }
                    else -> {
                        FIELD_ERROR_CNPJ
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

fun TextInputLayout.mask(mask: String) {
    var isUpdating = false
    var oldString = ""
    editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            error = null
            val input = s.toString()
            if (isUpdating) {
                oldString = input
                isUpdating = false
                return
            }

            isUpdating = true
            if (input.isEmpty()) return

            val inputWithMask = if (input.length > oldString.length) {
                input.getOnlyNumbers().mask(mask)
            } else {
                input
            }

            editText?.apply {
                setText(inputWithMask)
                setSelection(inputWithMask.length)
            }

        }
    })
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
