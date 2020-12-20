package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.ext.configAdapter
import br.com.joaoov.ext.format
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.showToast
import br.com.joaoov.ui.NoFilterAdapter
import kotlinx.android.synthetic.main.fragment_risk_create.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class RiskCreateFragment : Fragment(R.layout.fragment_risk_create) {

    private val args by navArgs<RiskCreateFragmentArgs>()
    private val mViewModel: RiskViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAutoCompleteTextViews()
        observeResourceRisk()
        button.setOnClickListener {
            // TODO: 20/12/20 validar campos obrigatorios
            // TODO: 20/12/20 salvar
            val risk = Risk(
                functionId = args.function.id,
                riskFactor = textInputLayoutRiskFactor.getString(),
                generatingSource = textInputLayoutGeneratingSource.getString(),
                intensityConcentration = textInputLayoutIntensityConcentration.getString(),
                levelAction = textInputLayoutLevelAction.getString(),
                NR15 = textInputLayoutNR15.getString(),
                ACGIH = textInputLayoutACGIH.getString(),
                trajectory = textInputLayoutTrajectory.getString(),
                eliminationNeutralization = textInputLayoutEliminationNeutralization.getString(),
                exposureMode = textInputLayoutExposureMode.getString(),
                sourceMethodology = textInputLayoutSourceMethodology.getString(),
                degreeOfRisk = textInputLayoutDegreeOfRisk.getString(),
                date = Date().format()
            )
            mViewModel.save(risk)
            showToast(R.string.message_success_created)
            findNavController().popBackStack()
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
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val item = adapterView.adapter.getItem(position) as String
                val category = "${item.split(".")[0]}.%"
                getAgents(category)
            }
        autoCompleteTextViewTrajectory.configAdapter(requireContext(), R.array.tragetoria)
        autoCompleteTextViewEliminationNeutralization.configAdapter(
            requireContext(),
            R.array.eliminacao_neutralizacao
        )
        autoCompleteTextViewExposureMode.configAdapter(requireContext(), R.array.modo_exposicao)
        autoCompleteTextViewSourceMethodology.configAdapter(
            requireContext(),
            R.array.fonte_metodologia
        )
        autoCompleteTextViewDeGreeOfRisk.configAdapter(requireContext(), R.array.grau_risco)
    }
}