package br.com.joaoov.ext

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