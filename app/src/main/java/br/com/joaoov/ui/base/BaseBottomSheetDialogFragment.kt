package br.com.joaoov.ui.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment(
    private val isFullLayout: Boolean = false
) : BottomSheetDialogFragment() {

    var onCancel: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancel?.invoke()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isFullLayout)
            dialog?.setOnShowListener { dialog ->
                val d = dialog as BottomSheetDialog
                val bottomSheet =
                    d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.peekHeight = bottomSheet.height
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        super.onViewCreated(view, savedInstanceState)
    }

}