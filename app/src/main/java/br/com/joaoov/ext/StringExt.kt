package br.com.joaoov.ext

import java.util.*

private const val DEFAULT_EMPTY = "N.A."

fun String.formatToArea() = "$this m²"

fun String.formatToMeters() = "$this m"

fun String?.formatFirstChar(): String {
    if (this.isNullOrEmpty()) return ""
    val text = this.trim()
    val first = if (text.first() == '0' && text.length > 1) {
        text[1]
    } else {
        text.first()
    }
    return first.toUpperCase().toString()
}

fun String.toUpperCaseWithLocale() = this.toUpperCase(Locale("pt", "BR"))

fun List<String>.addDefaultResource(): List<String> {
    val mutableList = mutableListOf(DEFAULT_EMPTY)
    mutableList.addAll(this)
    return mutableList.toList()
}