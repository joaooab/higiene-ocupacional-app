package br.com.joaoov.ui.company

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.R
import br.com.joaoov.data.Company
import br.com.joaoov.ext.format
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.fragment_company_create.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CompanyCreateFragment : Fragment(R.layout.fragment_company_create) {

    private val viewModel: CompanyViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSaveButton()
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val companyName = textInputLayoutCompany.getString()
            if (companyName.isEmpty()) {
                textInputLayoutCompany.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val company = Company(
                name = companyName,
                date = Date().format()
            )
            viewModel.salvar(company)
            showToast(R.string.message_success_created)
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
