package br.com.joaoov.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import br.com.joaoov.Constants.EMAIL_SUPPORT

fun Context.showToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.sendSupportEmail(subject: String = "Suporte") {
    val addresses = arrayOf(EMAIL_SUPPORT)
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, addresses)
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    if (intent.resolveActivity(this.packageManager) != null) {
        startActivity(intent)
    }
}
