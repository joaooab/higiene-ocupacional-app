package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.MainActivity
import br.com.joaoov.Path
import br.com.joaoov.R
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.departament.Departament
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.viewModel


class AmbientListFragment : Fragment(R.layout.fragment_ambient) {

    private val arguments by navArgs<AmbientListFragmentArgs>()
    private val viewModel: AmbientViewModel by viewModel()
    private val adapter: AmbientListAdapter by lazy {
        AmbientListAdapter { company ->
            navigateToFunctionFragment(company)
        }
    }
    private lateinit var departament: Departament

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        departament = arguments.departament
        (activity as MainActivity).addPath(
            Path(
                Path.DEPARTAMENT_TYPE,
                Path.DEPARTAMENT_ICON,
                departament.name
            )
        )
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
        (activity as MainActivity).removePath()
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

}
