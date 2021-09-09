package br.com.joaoov.ext

import java.util.*
import java.util.regex.Pattern

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

fun Any?.toStringOrEmpty() = this?.toString() ?: ""

fun String.getOnlyNumbers(): String = replace("[^0-9]".toRegex(), "")

