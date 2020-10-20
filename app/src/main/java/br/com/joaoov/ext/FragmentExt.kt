package br.com.joaoov.ext

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Fragment.supportFragmentManager(execute: FragmentManager.() -> Unit) {
    val supportFragmentManager = activity?.supportFragmentManager
        ?: throw IllegalArgumentException("Activity null")
    execute(supportFragmentManager)
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: Int) {
    showToast(getString(message))
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboardFrom(it) }
}

fun Context.hideKeyboardFrom(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
