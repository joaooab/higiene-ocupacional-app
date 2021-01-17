package br.com.joaoov.ui.export

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.State
import br.com.joaoov.data.local.report.Report
import br.com.joaoov.ext.*
import kotlinx.android.synthetic.main.fragment_export_send_report.*
import org.koin.android.viewmodel.ext.android.viewModel

class ExportSendReportFragment : Fragment(R.layout.fragment_export_send_report) {

    private val viewModel: ExportViewModel by viewModel()
    private val arguments by navArgs<ExportSendReportFragmentArgs>()
    private val report = Report()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleObserve()
    }

    private fun handleObserve() {
        viewModel.getAllOfCompany(arguments.company).observe(viewLifecycleOwner, {
            report.data = it
        })

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
                    showToast(R.string.message_send_failure)
                    buttonSend.show()
                    progressBar.gone()
                    buttonSend.setText(R.string.action_send_again)
                }
            }
        })
    }

    private fun setupView() {
        textViewInfo.text = arguments.company.name
        textInputLayoutEmail.setTypeEmail()
        setupButtonSend()
    }

    private fun setupButtonSend() {
        buttonSend.setOnClickListener {
            val email = textInputLayoutEmail.getString()
            if (email.isEmpty()) {
                textInputLayoutEmail.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            report.email = email
            viewModel.send(report)
        }
    }

}