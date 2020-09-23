package br.com.joaoov.ui.company

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.ComponentesVisuais
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.R
import br.com.joaoov.data.Company
import br.com.joaoov.ext.format
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.fragment_company_create.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CompanyCreateFragment : Fragment(R.layout.fragment_company_create) {

    private val viewModel: CompanyCreateViewModel by viewModel()
    private val estadoViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoViewModel.temComponentes = ComponentesVisuais(true)
        configurarBotaoSalvar()
    }

    private fun configurarBotaoSalvar() {
        buttonSalvar.setOnClickListener {
            val company = Company(
                name = editTextEmpresa.getString(),
                date = Date().format()
            )
            viewModel.salvar(company)
            showToast("Criado com sucesso")
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

}
