package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.ComponentesVisuais
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.R
import br.com.joaoov.data.Ambient
import br.com.joaoov.data.Company
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AmbientListFragment : Fragment(R.layout.fragment_ambient) {

    private val arguments by navArgs<AmbientListFragmentArgs>()
    private val viewModel: AmbientViewModel by viewModel()
    private val estadoViewModel: EstadoAppViewModel by sharedViewModel()
    private lateinit var adapter: AmbientListAdapter
    private lateinit var company: Company

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoViewModel.temComponentes = ComponentesVisuais(true)
        company = arguments.company
        bindCompany()
        observeAmbients()
        setupFab()
        setupAdapter()
    }

    private fun bindCompany() {
        textViewCompany.text = company.name
    }

    private fun setupFab() {
        fab.setOnClickListener {
            navigateToCreateFragment()
        }
    }

    private fun setupAdapter() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(divisor)
        adapter = AmbientListAdapter { company ->
            navigateToAmbientFragment(company)
        }
        recyclerView.adapter = adapter
    }


    private fun navigateToCreateFragment() {
        val direction =
            AmbientListFragmentDirections.actionLevantamentoFragmentToAmbientCreateFragment(company)
        findNavController().navigate(direction)
    }

    private fun observeAmbients() {
        viewModel.getAmbients(company).observe(viewLifecycleOwner, Observer {
            adapter.refresh(it)
        })
    }

    private fun navigateToAmbientFragment(ambient: Ambient) {
        val direction =
            AmbientListFragmentDirections.actionLevantamentoFragmentToAmbientDetailFragment(
                ambient,
                company
            )
        findNavController().navigate(direction)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_export -> {
                exportReport()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun exportReport() {
        viewModel.exportReport(company)
    }

}
