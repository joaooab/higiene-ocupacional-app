package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.R
import kotlinx.android.synthetic.main.fragment_risk.*

class RiskListFragment : Fragment(R.layout.fragment_risk) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            navigateToCreateRiskFragment()
        }
    }

    private fun navigateToCreateRiskFragment() {
        val action = RiskListFragmentDirections.actionRiskListFragmentToRiskCreateFragment()
        findNavController().navigate(action)
    }
}