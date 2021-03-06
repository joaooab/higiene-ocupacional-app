package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.Path
import br.com.joaoov.R
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.ext.slideUp
import br.com.joaoov.ui.component.AlertDialogCustom
import br.com.joaoov.ui.component.openMoveDialog
import kotlinx.android.synthetic.main.fragment_risk.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RiskListFragment : Fragment(R.layout.fragment_risk) {

    private val args by navArgs<RiskListFragmentArgs>()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val viewModel: RiskViewModel by viewModel()
    private lateinit var function: Function
    private val adapter by lazy {
        RiskListAdapter(
            onClick = {},
            onEditClick = { navigateToEditFragment(it) },
            onDuplicateClick = {
                AlertDialogCustom(requireContext())
                    .showDuplicateDialog { viewModel.duplicate(it) }
            },
            onMoveClick = { openMoveDialog(it) },
            onDeleteClick = {
                AlertDialogCustom(requireContext())
                    .showDeleteDialog { viewModel.delete(it) }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        function = args.funcion
        componentViewModel.withComponents = Components(path = true)
        componentViewModel.addPath(Path(Path.FUNCTION_PATH, function.name))
        setupView()
        handleObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        componentViewModel.removePath()
    }

    private fun setupView() {
        setupFab()
        setupAdapter()
    }

    private fun setupFab() {
        fab.setOnClickListener {
            navigateToCreateRiskFragment()
        }
    }

    private fun handleObserve() {
        viewModel.getRisks(function).observe(viewLifecycleOwner, {
            adapter.refresh(it)
            fab.slideUp()
        })
    }

    private fun setupAdapter() {
        val divider = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = adapter
    }

    private fun navigateToCreateRiskFragment() {
        val action = RiskListFragmentDirections.actionRiskListFragmentToRiskCreateFragment(function)
        findNavController().navigate(action)
    }

    private fun navigateToEditFragment(risk: Risk) {
        val direction = RiskListFragmentDirections.actionRiskListFragmentToRiskEditFragment(risk)
        findNavController().navigate(direction)
    }
}