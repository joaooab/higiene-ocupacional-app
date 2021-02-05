package br.com.joaoov.ui.function

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.ext.*
import br.com.joaoov.ui.component.WorkdayFragment
import kotlinx.android.synthetic.main.fragment_funciton_edit.*
import kotlinx.android.synthetic.main.include_funciton_form.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FunctionEditFragment : Fragment(R.layout.fragment_funciton_edit) {

    private val arguments by navArgs<FunctionEditFragmentArgs>()
    private val viewModel: FunctionViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = true)
        setupView()
    }

    private fun setupView() {
        setupDraft()
        setupSaveButton()
        setupWorkday()
    }

    private fun setupDraft() {
        val function = viewModel.functionDraft ?: arguments.function
        textInputLayoutFunction.setString(function.name)
        textInputLayoutDescription.setString(function.description)
        textInputLayoutAmount.setString(function.amount.toStringOrEmpty())
        textInputLayoutWorkDay.setString(function.workday)
    }

    private fun setupWorkday() {
        editTextWorkDay.setOnClickListener {
            viewModel.functionDraft = createFunction()
            setFragmentResultListener(WorkdayFragment.REQUEST_KEY) { _, bundle ->
                bundle.getString(WorkdayFragment.RESULT_KEY, "").let { workDay ->
                    if (workDay.isNotEmpty()) {
                        textInputLayoutWorkDay.setString(workDay)
                    }
                }
            }
            findNavController().navigate(R.id.workdayFragment)
        }
    }

    private fun setupSaveButton() {
        buttonEdit.setOnClickListener {
            val functionName = textInputLayoutFunction.getString()
            if (functionName.isEmpty()) {
                textInputLayoutFunction.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val function = createFunction()
            viewModel.update(function)
            findNavController().popBackStack()
        }
    }

    private fun createFunction(): Function {
        return arguments.function.copy(
            name = textInputLayoutFunction.getString(),
            description = textInputLayoutDescription.getString(),
            workday = textInputLayoutWorkDay.getString(),
            amount = textInputLayoutAmount.getInt()
        )
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
