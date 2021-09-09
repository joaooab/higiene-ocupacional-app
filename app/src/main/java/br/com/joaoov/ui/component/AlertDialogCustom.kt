package br.com.joaoov.ui.component

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import br.com.joaoov.R
import br.com.joaoov.data.local.billing.Billing
import br.com.joaoov.data.local.billing.isTrialPlan
import br.com.joaoov.ext.supportFragmentManager
import br.com.joaoov.ui.component.move.MoveDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialogCustom(private val context: Context) {

    fun showDeleteDialog(onPositiveButton: () -> Unit): AlertDialog =
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.message_alert)
            .setMessage(R.string.message_alert_delete)
            .setPositiveButton(R.string.action_confirm) { _, _ ->
                onPositiveButton()
            }
            .setNegativeButton(R.string.action_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()


    fun showDuplicateDialog(onPositiveButton: () -> Unit): AlertDialog =
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.message_alert)
            .setMessage(R.string.message_alert_duplicate)
            .setPositiveButton(R.string.action_confirm) { _, _ ->
                onPositiveButton()
            }
            .setNegativeButton(R.string.action_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    fun showPaylmentDialog(billing: Billing, onPositiveButton: () -> Unit): AlertDialog {
        val isTrialPlan = billing.isTrialPlan()
        val message = if (isTrialPlan) {
            R.string.message_alert_trial_plan
        } else {
            R.string.message_alert_payment
        }

        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(R.string.message_alert)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.action_ok) { _, _ ->
                onPositiveButton()
            }

        if (!isTrialPlan) {
            builder.setNegativeButton(R.string.action_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }

        return builder.show()
    }
}

fun Fragment.openMoveDialog(item: Any) {
    supportFragmentManager {
        MoveDialog.newInstance(item).show(this, "")
    }
}