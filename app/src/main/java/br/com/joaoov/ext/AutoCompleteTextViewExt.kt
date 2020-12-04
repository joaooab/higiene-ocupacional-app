package br.com.joaoov.ext

import android.content.Context
import android.widget.AutoCompleteTextView
import br.com.joaoov.R
import br.com.joaoov.ui.NoFilterAdapter

fun AutoCompleteTextView.configAdapter(context: Context, array: Int): Unit {
    this.setAdapter(
        NoFilterAdapter(
            context,
            R.layout.drop_down_item,
            resources.getStringArray(array)
        )
    )
}