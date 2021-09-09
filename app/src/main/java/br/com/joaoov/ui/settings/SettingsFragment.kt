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
import br.com.joaoov.data.local.billing.Billing
import br.com.joaoov.data.local.billing.BillingState
import br.com.joaoov.data.remote.user.isCompanyUser
import br.com.joaoov.data.remote.user.isLegalEntity
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.sendSupportEmail
import br.com.joaoov.ext.setVisible
import br.com.joaoov.ext.show
import br.com.joaoov.ui.billing.BillingViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.item_payment.*
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
        setupSupport()
    }

    private fun setupSupport() {
        textViewSupport.setOnClickListener {
            requireContext().sendSupportEmail()
        }
    }

    private fun handleObserver() {
        observeBilling()
    }

    private fun observeBilling() {
        lifecycleScope.launchWhenStarted {
            billingViewModel.billingState.collect { state ->
                when (state) {
                    is BillingState.Payed -> {
                        setupPurchasedPayments(state.billing)
                    }
                    else -> {
                        settingEmptyPayments.text = getString(R.string.message_payment_error)
                    }
                }
            }
        }
    }

    private fun setupPurchasedPayments(billing: Billing) {
        when {
            user.isCompanyUser() -> {
                setupCompanyUserPayments()
            }
            billing.purchase != null -> {
                settingEmptyPayments.gone()
                includeItemPayment.show()
                val sku = billing.productId
                billingViewModel.getBillingPlan(sku).observe(viewLifecycleOwner, {
                    itemPaymentTitle.text = it?.name.orEmpty()
                })

                itemPaymentChange.setOnClickListener {
                    navigateToBillingList()
                }
                itemPaymentManager.setOnClickListener {
                    val url = String.format(
                        PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL,
                        sku, requireContext().applicationContext.packageName
                    )
                    val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
                    startActivity(intent)
                }
            }
        }
    }

    private fun setupCompanyUserPayments() {
        settingEmptyPayments.text = getString(R.string.message_company_user_payment)
    }

    private fun setupPayments() {
        settingEmptyPayments.setOnClickListener {
            navigateToBillingList()
        }
    }

    private fun navigateToBillingList() {
        val direction = SettingsFragmentDirections.actionSettingsFragmentToBillingListFragment()
        findNavController().navigate(direction)
    }

    private fun setupActions() {
        settingChangePassword.setOnClickListener {
            navigateToUpdateUser()
        }

        settingAccessKey.setVisible(user.isLegalEntity())
        settingAccessKey.setOnClickListener {
            navigateToAccessKey()
        }

        settingSync.setOnClickListener {
            syncViewModel.forceSyncronize()
        }

        settingLogout.setOnClickListener {
            (requireActivity() as MainActivity).logout()
        }
    }

    private fun navigateToAccessKey() {
        val direction =
            SettingsFragmentDirections.actionSettingsFragmentToSettingsAccesskeyFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToUpdateUser() {
        val direction = SettingsFragmentDirections.actionSettingsFragmentToUserUpdateFragment()
        findNavController().navigate(direction)
    }

    private fun setupProfile() {
        user?.let { user ->
            textPersonName.text = user.name
            textPersonEmail.text = getString(R.string.label_email, user.username)
        }
    }

}