package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.R
import br.com.joaoov.ui.ChooseAgentDialog
import kotlinx.android.synthetic.main.fragment_risk.*
import org.koin.android.viewmodel.ext.android.viewModel

class RiskListFragment : Fragment(R.layout.fragment_risk) {

    private val mViewModel: RiskViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            showAgentDialog()
        }
    }

    private fun navigateToCreateRiskFragment(agent: String) {
        val action = RiskListFragmentDirections.actionRiskListFragmentToRiskCreateFragment()
        findNavController().navigate(action)
    }

    private fun showAgentDialog() {
        val dialog = ChooseAgentDialog.newInstance { agent ->
            navigateToCreateRiskFragment(agent)
        }
        dialog.show(childFragmentManager, ChooseAgentDialog::class.java.name)
    }
}