package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import br.com.joaoov.R
import br.com.joaoov.ext.configAdapter
import br.com.joaoov.ui.NoFilterAdapter
import kotlinx.android.synthetic.main.fragment_risk_create.*
import org.koin.android.viewmodel.ext.android.viewModel

class RiskCreateFragment : Fragment(R.layout.fragment_risk_create) {

    private val mViewModel: RiskViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAutoCompleteTextViews()
        observeResourceRisk()
        button.setOnClickListener {

        }
    }

    private fun observeResourceRisk() {
        mViewModel.getResourceRisks().observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, it)
            autoCompleteTextViewGeneratingSource.setAdapter(adapter)
        })
    }

    private fun getAgents(category: String) {
        mViewModel.getAgents(category).observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, it)
            autoCompleteTextViewRiskFactor.setAdapter(adapter)
        })
    }

    private fun configAutoCompleteTextViews() {
        autoCompleteTextViewAgents.setAdapter(
            NoFilterAdapter(
                requireContext(),
                R.layout.drop_down_item,
                resources.getStringArray(R.array.agents)
            )
        )
        autoCompleteTextViewAgents.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                val item = p0.adapter.getItem(p2) as String
                val category = "${item.split(".")[0]}.%"
                getAgents(category)
            }
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