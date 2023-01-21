package br.com.joaoov.ui.export

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.joaoov.BuildConfig
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.State
import br.com.joaoov.data.local.report.Report
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.handle
import br.com.joaoov.ext.showToast
import br.com.joaoov.ext.toUpperCaseWithLocale
import br.com.joaoov.ui.component.ValidatorEditText
import br.com.joaoov.ui.component.ValidatorEditTextBuilder
import br.com.joaoov.ui.component.ValidatorEditTextType
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.fragment_export_send_report.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ExportSendReportFragment : Fragment(R.layout.fragment_export_send_report) {

    private val viewModel: ExportViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val arguments by navArgs<ExportSendReportFragmentArgs>()
    private lateinit var validator: ValidatorEditText
    private val report = Report()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = false, menu = false)
        validator = ValidatorEditTextBuilder()
            .addField(textInputLayoutEmail, ValidatorEditTextType.Email(isRequired = true))
            .build()

        setupView()
        handleObserve()
    }

    private fun handleObserve() {
        observeAllOfCompany()
        observeSendState()
    }

    private fun observeAllOfCompany() {
        viewModel.getAllOfCompany(arguments.company).observe(viewLifecycleOwner) {
            report.data = it
        }
    }

    private fun observeSendState() {
        viewModel.sendState.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    buttonSend.startLoading()
                }
                is State.Success -> {
                    showAdd()
                    showToast(R.string.message_send_success)
                    buttonSend.endLoading()
                    buttonSend.setText(R.string.action_send_again)
                }
                is State.Error -> {
                    it.throwable.handle(requireContext())
                    buttonSend.endLoading()
                    buttonSend.setText(R.string.action_send_again)
                }
            }
        }
    }

    private fun showAdd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            BuildConfig.AD_INTERSTITIAL,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(requireActivity())
                }
            }
        )
    }

    private fun setupView() {
        textViewInfo.text = arguments.company.name.toUpperCaseWithLocale()
        setupButtonSend()
    }

    private fun setupButtonSend() {
        buttonSend.setOnClickListener {
            if (validator.validate()) {
                val email = textInputLayoutEmail.getString()
                report.email = email
                viewModel.send(report)
            }
        }
    }
}
