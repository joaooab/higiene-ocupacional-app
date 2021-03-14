package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.Path
import br.com.joaoov.Path.Companion.DEPARTAMENT_PATH
import br.com.joaoov.R
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.ui.component.GenericDialog
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class AmbientListFragment : Fragment(R.layout.fragment_ambient) {

    private val arguments by navArgs<AmbientListFragmentArgs>()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val viewModel: AmbientViewModel by viewModel()
    private val adapter: AmbientListAdapter by lazy {
        AmbientListAdapter(
            onClick = { navigateToFunctionFragment(it) },
            onEditClick = { navigateToEditFragment(it) },
            onDuplicateClick = { viewModel.duplicate(it) },
            onDeleteClick = {
                GenericDialog(requireContext())
                    .showDeleteDialog { viewModel.delete(it) }
            }
        )
    }

    private lateinit var departament: Departament

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        departament = arguments.departament
        componentViewModel.withComponents = Components(path = true)
        componentViewModel.addPath(Path(DEPARTAMENT_PATH, departament.name))
        handleObserve()
        setupView()
    }

    private fun handleObserve() {
        observeAmbients()
    }

    private fun setupView() {
        setupFab()
        setupAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        componentViewModel.removePath()
    }

    private fun setupFab() {
        fab.setOnClickListener {
            navigateToCreateFragment()
        }
    }

    private fun setupAdapter() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(divisor)
        recyclerView.adapter = adapter
    }


    private fun navigateToCreateFragment() {
        val direction =
            AmbientListFragmentDirections.actionAmbientListFragmentToAmbientCreateFragment(
                departament
            )
        findNavController().navigate(direction)
    }

    private fun observeAmbients() {
        viewModel.getAmbients(departament).observe(viewLifecycleOwner, Observer {
            adapter.refresh(it)
        })
    }

    private fun navigateToFunctionFragment(ambient: Ambient) {
        val direction =
            AmbientListFragmentDirections.actionAmbientListFragmentToFunctionListFragment(ambient)
        findNavController().navigate(direction)
    }

    private fun navigateToEditFragment(ambient: Ambient) {
        val direction =
            AmbientListFragmentDirections.actionAmbientListFragmentToAmbientEditFragment(ambient)
        findNavController().navigate(direction)
    }

}
