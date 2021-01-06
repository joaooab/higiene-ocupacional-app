package br.com.joaoov.ui.function

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.MainViewModel
import br.com.joaoov.Path
import br.com.joaoov.Path.Companion.AMBIENT_PATH
import br.com.joaoov.R
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.function.Function
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FunctionListFragment : Fragment(R.layout.fragment_company) {

    private val arguments by navArgs<FunctionListFragmentArgs>()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: FunctionViewModel by viewModel()
    private val adapter: FunctionListAdapter by lazy {
        FunctionListAdapter(
            onClick = { navigateToRiskFragment(it) },
            onEditClick = { navigateToEditFragment(it) },
            onDeleteClick = { viewModel.delete(it) }
        )
    }
    private lateinit var ambient: Ambient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ambient = arguments.ambient
        mainViewModel.addPath(Path(AMBIENT_PATH, ambient.name))
        handleObserve()
        setupView()
    }

    private fun setupView() {
        setupAdapter()
        setupFab()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.removePath()
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
            FunctionListFragmentDirections.actionFunctionListFragmentToFunctionCreateFragment(
                ambient
            )
        findNavController().navigate(direction)
    }

    private fun handleObserve() {
        viewModel.getFunctions(ambient).observe(viewLifecycleOwner, Observer {
            adapter.refresh(it)
        })
    }

    private fun navigateToEditFragment(function: Function) {
        val direction =
            FunctionListFragmentDirections.actionFunctionListFragmentToFunctionEditFragment(
                function
            )
        findNavController().navigate(direction)
    }

    private fun navigateToRiskFragment(function: Function) {
        val direction = FunctionListFragmentDirections.actionFunctionListFragmentToRiskListFragment(function)
        findNavController().navigate(direction)
    }

}
