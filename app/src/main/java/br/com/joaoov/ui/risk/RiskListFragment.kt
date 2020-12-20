package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.local.function.Function
import kotlinx.android.synthetic.main.fragment_risk.*
import org.koin.android.viewmodel.ext.android.viewModel

class RiskListFragment : Fragment(R.layout.fragment_risk) {

    private val args by navArgs<RiskListFragmentArgs>()
    private val mViewModel: RiskViewModel by viewModel()
    private lateinit var function: Function

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        function = args.funcion
        fab.setOnClickListener {
            navigateToCreateRiskFragment()
        }
    }

    private fun navigateToCreateRiskFragment() {
        val action = RiskListFragmentDirections.actionRiskListFragmentToRiskCreateFragment(function)
        findNavController().navigate(action)
    }
}