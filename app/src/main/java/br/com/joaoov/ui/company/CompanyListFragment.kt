package br.com.joaoov.ui.company

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.ext.findNavControllerSafely
import br.com.joaoov.ext.slideUp
import br.com.joaoov.ui.component.AlertDialogCustom
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CompanyListFragment : Fragment(R.layout.fragment_company) {

    private val viewModel: CompanyViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val adapter: CompanyListAdapter by lazy {
        CompanyListAdapter(
            onClick = { navigateToDepartamentFragment(it) },
            onEditClick = { navigateToEditCompanyFragment(it) },
            onDuplicateClick = {
                AlertDialogCustom(requireContext())
                    .showDuplicateDialog { viewModel.duplicate(it) }
            },
            onDeleteClick = {
                AlertDialogCustom(requireContext())
                    .showDeleteDialog { viewModel.delete(it) }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = true)
        componentViewModel.clearPaths()
        setupView()
        handleObserve()
    }

    private fun setupView() {
        setupAdapter()
        setupFab()
    }

    private fun setupAdapter() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(divisor)
        recyclerView.adapter = adapter
    }

    private fun setupFab() {
        fab.setOnClickListener {
            navigateToCreateFragment()
        }
    }

    private fun navigateToCreateFragment() {
        val direction =
            CompanyListFragmentDirections.actionCompanyListFragmentToCompanyCreateFragment()
        findNavControllerSafely()?.navigate(direction)
    }

    private fun handleObserve() {
        observeCompanies()
    }

    private fun observeCompanies() {
        viewModel.getCompanies().observe(viewLifecycleOwner, Observer {
            adapter.refresh(it)
            fab.slideUp()
        })
    }

    private fun navigateToDepartamentFragment(company: Company) {
        val direction =
            CompanyListFragmentDirections.actionCompanyListFragmentToDepartamentListFragment(company)
        findNavControllerSafely()?.navigate(direction)
    }

    private fun navigateToEditCompanyFragment(company: Company) {
        val direction =
            CompanyListFragmentDirections.actionCompanyListFragmentToCompanyEditFragment(company)
        findNavControllerSafely()?.navigate(direction)
    }

}
