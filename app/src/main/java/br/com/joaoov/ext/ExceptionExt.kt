package br.com.joaoov.ext

import android.content.Context
import br.com.joaoov.MainActivity
import br.com.joaoov.R
import br.com.joaoov.data.BusinessError
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection

private fun Throwable.handleMessage(context: Context): String {
    return when (this) {
        is ConnectException -> context.getString(R.string.throwable_connect)
        is HttpException -> handleServerError(this, context)
        else -> genericError(context)
    }
}

private fun genericError(context: Context) = context.getString(R.string.throwable_generic)

fun Throwable.handle(context: Context, showToast: Boolean = true): String {
    return if (this is HttpException && this.code() == HttpURLConnection.HTTP_FORBIDDEN) {
        (context as MainActivity).logout()
        val message = context.getString(R.string.throwable_session)
        context.showToast(message)

        message
    } else {
        val message = handleMessage(context)
        if (showToast) {
            context.showToast(message)
        }

        message
    }
}

private fun handleServerError(throwable: HttpException, context: Context): String {
    return try {
        val json = throwable.response()?.errorBody()?.string()
        val error = Gson().fromJson(json, BusinessError::class.java)
        val message = error.message
        if (message.isBlank()) {
            genericError(context)
        } else {
            message
        }
    } catch (e: Exception) {
        genericError(context)
    }
}