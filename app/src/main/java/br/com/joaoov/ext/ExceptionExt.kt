package br.com.joaoov.ext

import br.com.joaoov.data.BusinessError
import com.google.gson.Gson
import retrofit2.HttpException

private val GENERIC_ERROR = "Erro inesperado tente novamente mais tarde"

fun Throwable.handleError(): String {
    return if (this is HttpException) {
        val json = this.response()?.errorBody()?.string() ?: GENERIC_ERROR
        val error = Gson().fromJson(json, BusinessError::class.java)
        return error.message
    } else {
        GENERIC_ERROR
    }
}