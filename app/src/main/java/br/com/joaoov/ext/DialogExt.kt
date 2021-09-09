package br.com.joaoov.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.show(manager: FragmentManager) {
    show(manager, this.javaClass.simpleName)
}