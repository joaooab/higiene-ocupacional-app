package br.com.joaoov.ui.departament

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
import br.com.joaoov.Path.Companion.COMPANY_PATH
import br.com.joaoov.R
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.ext.slideUp
import br.com.joaoov.ui.component.AlertDialogCustom
import br.com.joaoov.ui.component.openMoveDialog
import kotlinx.android.synthetic.main.fragment_ambient.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DepartamentListFragment : Fragment(R.layout.fragment_departament) {

    private val arguments by navArgs<DepartamentListFragmentArgs>()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val viewModel: DepartamentViewModel by viewModel()
    private val adapter: DepartamentListAdapter by lazy {
        DepartamentListAdapter(
            onClick = { navigateToAmbientFragment(it) },
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
    private lateinit var company: Company

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        company = arguments.company
        componentViewModel.withComponents = Components(path = true)
        componentViewModel.addPath(Path(COMPANY_PATH, company.name))
        setupView()
        handleObserve()
    }

    private fun handleObserve() {
        observeDepartaments()
    }

    private fun setupView() {
        setupAdapter()
        configurarFab()
    }

    override fun onDestroy() {
        super.onDestroy()
        componentViewModel.removePath()
    }

    private fun setupAdapter() {
        val divisor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(divisor)
        recyclerView.adapter = adapter
    }

    private fun configurarFab() {
        fab.setOnClickListener {
            navigateToCreateFragment()
        }
    }

    private fun navigateToCreateFragment() {
        val direction =
            DepartamentListFragmentDirections.actionDepartamentListFragmentToDepartamentCreateFragment(
                company
            )
        findNavController().navigate(direction)
    }

    private fun observeDepartaments() {
        viewModel.getDepartaments(company).observe(viewLifecycleOwner, Observer {
            adapter.refresh(it)
            fab.slideUp()
        })
    }

    private fun navigateToAmbientFragment(departament: Departament) {
        val direction =
            DepartamentListFragmentDirections.actionDepartamentListFragmentToAmbientListFragment(
                departament
            )
        findNavController().navigate(direction)
    }

    private fun navigateToEditFragment(departament: Departament) {
        val direction =
            DepartamentListFragmentDirections.actionDepartamentListFragmentToDepartamentEditFragment(
                departament
            )
        findNavController().navigate(direction)
    }

}
