package br.com.joaoov.ui.export

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.R
import br.com.joaoov.data.local.company.Company
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.ext.android.inject

class ExportSelectCompanyFragment : Fragment(R.layout.fragment_export_select_company) {

    private val viewModel: ExportViewModel by inject()

    private val adapterCompany: ExportCompanyListAdapter by lazy {
        ExportCompanyListAdapter(
            onClick = {
                navigateToSendReportFragment(it)
            }
        )
    }

    private fun navigateToSendReportFragment(it: Company) {
        val direction =
            ExportSelectCompanyFragmentDirections.actionExportFragmentToExportSendReportFragment(it)
        findNavController().navigate(direction)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_export)?.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleObserve()
    }

    private fun handleObserve() {
        viewModel.getCompanies().observe(viewLifecycleOwner, {
            adapterCompany.refresh(it)
        })
    }

    private fun setupView() {
        setupAdapter()

    }

    private fun setupAdapter() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(divisor)
        recyclerView.adapter = adapterCompany
    }

}