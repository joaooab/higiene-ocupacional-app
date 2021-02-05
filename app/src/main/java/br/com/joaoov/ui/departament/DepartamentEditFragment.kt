package br.com.joaoov.ui.departament

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.setString
import kotlinx.android.synthetic.main.fragment_departament_edit.*
import kotlinx.android.synthetic.main.include_departament_form.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DepartamentEditFragment : Fragment(R.layout.fragment_departament_edit) {

    private val arguments by navArgs<DepartamentEditFragmentArgs>()
    private val viewModel: DepartamentViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = true)
        textInputLayoutDepartament.setString(arguments.departament.name)
        setupSaveButton()
    }

    private fun setupSaveButton() {
        buttonEdit.setOnClickListener {
            val departamentName = textInputLayoutDepartament.getString()
            if (departamentName.isEmpty()) {
                textInputLayoutDepartament.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val departament = arguments.departament.copy(name = departamentName)
            viewModel.update(departament)
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
