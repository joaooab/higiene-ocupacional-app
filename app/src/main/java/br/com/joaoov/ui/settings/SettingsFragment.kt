package br.com.joaoov.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.joaoov.*
import br.com.joaoov.Constants.PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL
import br.com.joaoov.data.remote.billing.BillingState
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.setVisible
import br.com.joaoov.ext.show
import br.com.joaoov.ui.billing.BillingViewModel
import com.android.billingclient.api.Purchase
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val syncViewModel: SyncViewModel by sharedViewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val billingViewModel: BillingViewModel by sharedViewModel()
    private val user = Session.user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(menu = false)
        setupView()
        handleObserver()
    }

    private fun setupView() {
        setupProfile()
        setupActions()
        setupPayments()
    }

    private fun handleObserver() {
        observeBilling()
    }

    private fun observeBilling() {
        lifecycleScope.launchWhenCreated {
            billingViewModel.billingState.collect { state ->
                when (state) {
                    is BillingState.Purchased -> {
                        setupPurchasedPayments(state.purchase)
                    }
                    is BillingState.Error -> {
                        settingEmptyPayments.text = getString(R.string.message_payment_error)
                    }
                    else -> {
                        if (user.isCompanyUser()) {
                            setupCompanyUserPayments()
                        }
                    }
                }
            }
        }
    }

    private fun setupPurchasedPayments(purchase: Purchase) {
        when {
            user.isCompanyUser() -> {
                setupCompanyUserPayments()
            }
            user.isLegalEntity() -> {

            }
            else -> {
                settingEmptyPayments.gone()
                val sku = purchase.skus.firstOrNull()
                settingPaymentManager.setOnClickListener {
                    val url = String.format(
                        PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL,
                        sku, requireContext().applicationContext.packageName
                    )
                    val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
                    startActivity(intent)
                }
                settingPaymentManager.text = sku.orEmpty()
                settingPaymentManager.show()
            }
        }
    }

    private fun setupCompanyUserPayments() {
        settingEmptyPayments.text = getString(R.string.message_company_user_payment)
    }

    private fun setupPayments() {
        settingEmptyPayments.setOnClickListener {
            val direction = SettingsFragmentDirections.actionSettingsFragmentToBillingListFragment()
            findNavController().navigate(direction)
        }
    }

    private fun setupActions() {
        settingChangePassword.setOnClickListener {
            val direction = SettingsFragmentDirections.actionSettingsFragmentToUserUpdateFragment()
            findNavController().navigate(direction)
        }

        settingAccessKey.setVisible(user.isLegalEntity())
        settingAccessKey.setOnClickListener {
            val direction =
                SettingsFragmentDirections.actionSettingsFragmentToSettingsAccesskeyFragment()
            findNavController().navigate(direction)
        }

        settingSync.setOnClickListener {
            syncViewModel.forceSyncronize()
        }

        settingLogout.setOnClickListener {
            (requireActivity() as MainActivity).logout()
        }
    }

    private fun setupProfile() {
        textPersonName.text = user.name
        textPersonEmail.text = getString(R.string.label_email, user.username)
    }

}