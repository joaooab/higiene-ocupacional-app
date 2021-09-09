package br.com.joaoov.ui.billing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.State
import br.com.joaoov.ext.gone
import br.com.joaoov.ext.show
import br.com.joaoov.ext.showToast
import br.com.joaoov.ui.base.ItemSingleSelect
import com.android.billingclient.api.SkuDetailsResult
import kotlinx.android.synthetic.main.fragment_billing.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BillingListFragment : Fragment(R.layout.fragment_billing) {

    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val viewModel: BillingViewModel by viewModel()
    private val adapter by lazy { BillingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(menu = false)
        setupView()
        handleObserve()
        viewModel.init(requireContext())
    }

    private fun setupView() {
        setupSubsList()
        setupContractButton()
    }

    private fun setupContractButton() {
        buttonContract.setOnClickListener {
            val skuSelected = viewModel.skuSelected
            if (skuSelected != null) {
                viewModel.contract(requireActivity(), skuSelected)
            } else {
                requireContext().showToast(R.string.message_error_no_plan_selected)
            }
        }
    }

    private fun setupSubsList() {
        recyclerView.adapter = adapter
        adapter.onClick = {
            viewModel.skuSelected = it?.data
        }
    }

    private fun handleObserve() {
        lifecycleScope.launchWhenStarted {
            viewModel.subsListState.collect { state ->
                when (state) {
                    is State.Loading -> {
                        loading.show()
                        recyclerView.gone()
                    }
                    is State.Success -> {
                        loading.gone()
                        recyclerView.show()
                        adapter.items = setupSkuDetailList(state.data).toMutableList()
                    }
                    is State.Error -> {
                        loading.show()
                        recyclerView.gone()
                    }
                }
            }
        }
    }

    private fun setupSkuDetailList(data: SkuDetailsResult) =
        data.skuDetailsList?.map { ItemSingleSelect(it) } ?: emptyList()

}