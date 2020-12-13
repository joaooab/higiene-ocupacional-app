package br.com.joaoov.ui.risk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.ext.configAdapter
import br.com.joaoov.ui.NoFilterAdapter
import kotlinx.android.synthetic.main.dialog_choose_agent.*
import kotlinx.android.synthetic.main.fragment_risk_create.*
import kotlinx.android.synthetic.main.fragment_risk_create.autoCompleteTextViewAgents
import org.koin.android.viewmodel.ext.android.viewModel

class RiskCreateFragment : Fragment(R.layout.fragment_risk_create) {

    private val mViewModel: RiskViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAutoCompleteTextViews()
        button.setOnClickListener {

        }
    }

    private fun getAgents(category: String) {
        mViewModel.getAgents(category).observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
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
        autoCompleteTextViewAgents.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                val item = p0.adapter.getItem(p2) as String
                val category = "${item.split(".")[0]}.%"
                getAgents(category)
            }
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