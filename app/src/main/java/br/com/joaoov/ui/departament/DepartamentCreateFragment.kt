package br.com.joaoov.ui.departament

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.Departament
import br.com.joaoov.ext.format
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.fragment_departament_create.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class DepartamentCreateFragment : Fragment(R.layout.fragment_departament_create) {

    private val arguments by navArgs<DepartamentCreateFragmentArgs>()
    private val viewModel: DepartamentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSaveButton()
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val departamentName = textInputLayoutDepartament.getString()
            if (departamentName.isEmpty()) {
                textInputLayoutDepartament.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val departament = Departament(
                companyId = arguments.company.id,
                name = departamentName,
                date = Date().format()
            )
            viewModel.salvar(departament)
            showToast("Criado com sucesso")
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
