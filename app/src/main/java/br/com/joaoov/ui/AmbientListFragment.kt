package br.com.joaoov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.joaoov.ComponentesVisuais
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.R
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AmbientListFragment : Fragment() {

    private val viewModel: AmbientViewModel by viewModel()
    private val estadoViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ambient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoViewModel.temComponentes = ComponentesVisuais(true)
        observarLevantamentos()
        configurarFab()
    }

    private fun configurarFab() {
        fab.setOnClickListener {
            val direction =
                AmbientListFragmentDirections.actionLevantamentoFragmentToAmbientCreateFragment()
            findNavController().navigate(direction)
        }
    }

    private fun observarLevantamentos() {
        viewModel.getLevantamentos().observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = AmbientListAdapter(it) {
                val direction =
                    AmbientListFragmentDirections.actionLevantamentoFragmentToAmbientCreateFragment()
                findNavController().navigate(direction)
            }
        })
    }

}
