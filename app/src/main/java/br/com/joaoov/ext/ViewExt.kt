package br.com.joaoov.ext

import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.doOnTextChanged
import br.com.joaoov.R
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun TextInputLayout.getString(): String {
    return try {
        this.editText?.text.toString()
    } catch (e: Exception) {
        ""
    }
}

fun TextInputLayout.getStringOrNull(): String? {
    return try {
        this.editText?.text.toString().ifBlank { null }
    } catch (e: Exception) {
        null
    }
}

fun TextInputLayout.getInt(): Int {
    return try {
        this.editText?.text.toString().toInt()
    } catch (e: Exception) {
        0
    }
}

fun TextInputLayout.setString(text: String) {
    this.editText?.setText(text)
}

fun TextInputLayout.getDouble(): Double? {
    return try {
        this.editText?.text.toString().toDouble()
    } catch (e: Exception) {
        null
    }
}

fun TextInputLayout.getBigdecimal(): BigDecimal? {
    return try {
        this.editText?.text.toString().toBigDecimal()
    } catch (e: Exception) {
        null
    }
}

fun TextInputLayout.setupData(data: List<*>) {
    (this.editText as AutoCompleteTextView).setAdapter(
        ArrayAdapter(
            context,
            R.layout.drop_down_item,
            data
        )
    )
    if (editText?.inputType == InputType.TYPE_NULL) {
        editText?.setOnFocusChangeListener { _, _ -> context.hideKeyboardFrom(this) }
    } else {
        setupTextChangedIcon()
    }
}

fun TextInputLayout.setupTextChangedIcon() {
    editText?.doOnTextChanged { text, _, _, _ ->
        if (text.isNullOrEmpty()) {
            this.endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
        } else {
            this.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
        }
    }

}

fun FloatingActionButton.slideUp() {
    val layoutParams = this.layoutParams
    if (layoutParams is CoordinatorLayout.LayoutParams) {
        val behavior = layoutParams.behavior
        if (behavior is HideBottomViewOnScrollBehavior) {
            behavior.slideUp(this)
        }
    }
}
