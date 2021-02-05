package br.com.joaoov.ui.company

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.R
import br.com.joaoov.data.local.company.Company
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.viewModel

class CompanyListFragment : Fragment(R.layout.fragment_company) {

    private val viewModel: CompanyViewModel by viewModel()
    private val adapter: CompanyListAdapter by lazy {
        CompanyListAdapter(
            onClick = { navigateToDepartamentFragment(it) },
            onEditClick = { navigateToEditCompanyFragment(it) },
            onDeleteClick = { viewModel.delete(it) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleObserve()
    }

    private fun setupView() {
        setupAdapter()
        setupFAb()
    }

    private fun setupAdapter() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(divisor)
        recyclerView.adapter = adapter
    }

    private fun setupFAb() {
        fab.setOnClickListener {
            navigateToCreateFragment()
        }
    }

    private fun navigateToCreateFragment() {
        val direction =
            CompanyListFragmentDirections.actionCompanyListFragmentToCompanyCreateFragment()
        findNavController().navigate(direction)
    }

    private fun handleObserve() {
        viewModel.getCompanies().observe(viewLifecycleOwner, Observer {
            adapter.refresh(it)
        })
    }

    private fun navigateToDepartamentFragment(company: Company) {
        val direction =
            CompanyListFragmentDirections.actionCompanyListFragmentToDepartamentListFragment(company)
        findNavController().navigate(direction)
    }

    private fun navigateToEditCompanyFragment(company: Company) {
        val direction =
            CompanyListFragmentDirections.actionCompanyListFragmentToCompanyEditFragment(company)
        findNavController().navigate(direction)
    }

}
