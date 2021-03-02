package br.com.joaoov.ui.component


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.joaoov.R
import br.com.joaoov.ext.getBigdecimal
import kotlinx.android.synthetic.main.dialog_area.*
import java.math.RoundingMode

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
            val width = textInputLayoutAreaWidth.getBigdecimal()
            val lenght = textInputLayoutAreaLenght.getBigdecimal()
            if (width == null) {
                textInputLayoutAreaWidth.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            if (lenght == null) {
                textInputLayoutAreaLenght.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val area = width.multiply(lenght).setScale(2, RoundingMode.UP)
            onFinished(area.toString())
            dismiss()
        }
    }

}