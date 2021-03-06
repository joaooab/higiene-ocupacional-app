package br.com.joaoov.ui.company

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
import kotlinx.android.synthetic.main.fragment_company_create.*
import kotlinx.android.synthetic.main.include_company_form.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CompanyEditFragment : Fragment(R.layout.fragment_company_edit) {

    private val arguemnts by navArgs<CompanyEditFragmentArgs>()
    private val viewModel: CompanyViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = true)
        textInputLayoutCompany.setString(arguemnts.company.name)
        setupSaveButton()
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val companyName = textInputLayoutCompany.getString()
            if (companyName.isEmpty()) {
                textInputLayoutCompany.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val company = arguemnts.company.copy(name = companyName)
            viewModel.update(company)
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
