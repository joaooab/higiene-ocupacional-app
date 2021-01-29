package br.com.joaoov.ui.component


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.joaoov.R
import br.com.joaoov.ext.getDouble
import kotlinx.android.synthetic.main.dialog_area.*

class AreaDialog : DialogFragment() {

    private lateinit var onFinished: (String) -> Unit

    companion object {
        fun newInstance(onFinished: (String) -> Unit): AreaDialog {
            val dialog = AreaDialog().apply {
                this.onFinished = onFinished
            }

            return dialog
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_area, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonCalc.setOnClickListener {
            val width = textInputLayoutAreaWidth.getDouble()
            val lenght = textInputLayoutAreaLenght.getDouble()
            if (width == null) {
                textInputLayoutAreaWidth.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            if (lenght == null) {
                textInputLayoutAreaLenght.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val area = width * lenght
            onFinished(area.toString())
            dismiss()
        }
    }

}