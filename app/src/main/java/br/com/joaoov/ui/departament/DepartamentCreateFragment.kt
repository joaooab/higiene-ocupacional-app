package br.com.joaoov.ui.departament

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.ext.format
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.requestAd
import br.com.joaoov.ui.billing.BillingViewModel
import kotlinx.android.synthetic.main.fragment_departament_create.*
import kotlinx.android.synthetic.main.fragment_departament_create.buttonSave
import kotlinx.android.synthetic.main.include_departament_form.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class DepartamentCreateFragment : Fragment(R.layout.fragment_departament_create) {

    private val arguments by navArgs<DepartamentCreateFragmentArgs>()
    private val viewModel: DepartamentViewModel by viewModel()
    private val billingViewModel: BillingViewModel by sharedViewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(true)
        requestAd(billingViewModel) { layout.addView(it, 0) }
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
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
