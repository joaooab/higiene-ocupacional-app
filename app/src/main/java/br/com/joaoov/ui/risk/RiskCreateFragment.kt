package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.ext.configAdapter
import kotlinx.android.synthetic.main.fragment_risk_create.*
import org.koin.android.viewmodel.ext.android.viewModel

class RiskCreateFragment : Fragment(R.layout.fragment_risk_create) {

    private val mViewModel: RiskViewModel by viewModel()
    private val arguments by navArgs<RiskCreateFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = "${arguments.agent.split(".")[0]}.%"
        mViewModel.getAgents(category).observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            spinner_agents.adapter = adapter
        })

        configAutoCompleteTextViews()
        button.setOnClickListener {

        }
    }

    private fun configAutoCompleteTextViews() {
        autoCompleteTextViewTrajectory.configAdapter(requireContext(), R.array.tragetoria)
        autoCompleteTextViewEliminationNeutralization.configAdapter(
            requireContext(),
            R.array.eliminacao_neutralizacao
        )
        autoCompleteTextViewExposureMode.configAdapter(requireContext(), R.array.modo_exposicao)
        autoCompleteTextViewSourceMetodology.configAdapter(
            requireContext(),
            R.array.fonte_metodologia
        )
        autoCompleteTextViewDeGreeOfRisk.configAdapter(requireContext(), R.array.grau_risco)
    }

}