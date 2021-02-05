package br.com.joaoov.ui.export

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.State
import br.com.joaoov.data.local.report.Report
import br.com.joaoov.ext.*
import kotlinx.android.synthetic.main.fragment_export_send_report.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ExportSendReportFragment : Fragment(R.layout.fragment_export_send_report) {

    private val viewModel: ExportViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val arguments by navArgs<ExportSendReportFragmentArgs>()
    private val report = Report()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = false)
        setupView()
        handleObserve()
    }

    private fun handleObserve() {
        observeAllOfCompany()
        observeSendState()
    }

    private fun observeAllOfCompany() {
        viewModel.getAllOfCompany(arguments.company).observe(viewLifecycleOwner, {
            report.data = it
        })
    }

    private fun observeSendState() {
        viewModel.sendState.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> {
                    buttonSend.gone()
                    progressBar.show()
                }
                is State.Success -> {
                    showToast(R.string.message_send_success)
                    buttonSend.show()
                    progressBar.gone()
                    buttonSend.setText(R.string.action_send_again)
                }
                is State.Error -> {
                    showToast(it.throwable.handleError())
                    buttonSend.show()
                    progressBar.gone()
                    buttonSend.setText(R.string.action_send_again)
                }
            }
        })
    }

    private fun setupView() {
        textViewInfo.text = arguments.company.name.toUpperCaseWithLocale()
        textInputLayoutEmail.setTypeEmail()
        setupButtonSend()
    }

    private fun setupButtonSend() {
        buttonSend.setOnClickListener {
            val email = textInputLayoutEmail.getString()
            if (!email.isValidEmail()) {
                textInputLayoutEmail.error = getString(R.string.message_invalid_email)
                return@setOnClickListener
            }
            report.email = email
            viewModel.send(report)
        }
    }

}