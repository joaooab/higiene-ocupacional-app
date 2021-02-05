package br.com.joaoov.ui.function

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.setString
import kotlinx.android.synthetic.main.fragment_funciton_create.*
import org.koin.android.viewmodel.ext.android.viewModel

class FunctionEditFragment : Fragment(R.layout.fragment_funciton_edit) {

    private val arguments by navArgs<FunctionEditFragmentArgs>()
    private val viewModel: FunctionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupSaveButton()
    }

    private fun setupView() {
        arguments.function.let {
            textInputLayoutFunction.setString(it.name)
            textInputLayoutDescription.setString(it.description)
            textInputLayoutWorkDay.setString(it.workday)
        }
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val functionName = textInputLayoutFunction.getString()
            if (functionName.isEmpty()) {
                textInputLayoutFunction.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val function = arguments.function.copy(
                name = functionName,
                description = textInputLayoutDescription.getString(),
                workday = textInputLayoutWorkDay.getString()
            )
            viewModel.update(function)
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
