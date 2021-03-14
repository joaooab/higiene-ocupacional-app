package br.com.joaoov.ui.component

import android.content.Context
import androidx.appcompat.app.AlertDialog
import br.com.joaoov.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GenericDialog(private val context: Context) {

    fun showDeleteDialog(onPositiveButton: () -> Unit): AlertDialog =
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.message_alert)
            .setMessage(R.string.message_alert_delete)
            .setPositiveButton(R.string.action_ok) { _, _ ->
                onPositiveButton()
            }
            .setNegativeButton(R.string.action_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

}