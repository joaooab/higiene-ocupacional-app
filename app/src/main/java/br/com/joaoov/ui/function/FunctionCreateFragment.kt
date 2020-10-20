package br.com.joaoov.ui.function

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.function.Function
import br.com.joaoov.ext.format
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.fragment_funciton_create.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FunctionCreateFragment : Fragment(R.layout.fragment_funciton_create) {

    private val arguments by navArgs<FunctionCreateFragmentArgs>()
    private val viewModel: FunctionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSaveButton()
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val functionName = textInputLayoutFunction.getString()
            if (functionName.isEmpty()) {
                textInputLayoutFunction.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val function = Function(
                name = functionName,
                ambientId = arguments.ambient.id,
                date = Date().format(),
                description = textInputLayoutDescription.getString(),
                workday = textInputLayoutWorkDay.getString()
            )
            viewModel.salvar(function)
            showToast(R.string.message_success_created)
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
