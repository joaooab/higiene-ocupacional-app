package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.local.resource.ResourceAgentCategory
import br.com.joaoov.data.local.resource.ResourceRiskCategory
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.data.local.risk.Tolerance
import br.com.joaoov.ext.format
import br.com.joaoov.ext.getString
import kotlinx.android.synthetic.main.fragment_risk_create.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class RiskCreateFragment : Fragment(R.layout.fragment_risk_create) {

    private val arguments by navArgs<RiskCreateFragmentArgs>()
    private val viewModel: RiskViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleObserve()
        setupSaveButton()
    }

    private fun setupView() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.drop_down_item,
            ResourceAgentCategory.getFormatedValues()
        )
        autoCompleteTextViewAgents.setAdapter(adapter)
        autoCompleteTextViewAgents.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val item = adapterView.adapter.getItem(position).toString()
                val category = ResourceAgentCategory.fromFormatedValue(item)
                viewModel.changeCategory(category)
            }
    }

    private fun handleObserve() {
        observeAgentCategory()
        observeResource(
            ResourceRiskCategory.GENERATED_SOURCE,
            autoCompleteTextViewGeneratingSource
        )
        observeResource(
            ResourceRiskCategory.TRAJECTORY,
            autoCompleteTextViewTrajectory
        )
        observeResource(
            ResourceRiskCategory.ELIMINATION_NEUTRALIZATION,
            autoCompleteTextViewEliminationNeutralization
        )
        observeResource(
            ResourceRiskCategory.EXPOSURE,
            autoCompleteTextViewExposure
        )
        observeResource(
            ResourceRiskCategory.DEGREE_OF_RISK,
            autoCompleteTextViewDegreeOfRisk
        )
        observeResource(
            ResourceRiskCategory.METHODOLOGY,
            autoCompleteTextViewSourceMethodology
        )
    }

    private fun observeAgentCategory() {
        viewModel.category.observe(viewLifecycleOwner, { category ->
            autoCompleteTextViewRiskFactor.setText("")
            if (category != null && category != ResourceAgentCategory.UNKNOW) {
                textInputLayoutRiskFactor.isEnabled = true
                autoCompleteTextViewRiskFactor.isEnabled = true
                viewModel.getResourceAgentByCategory(category).observe(viewLifecycleOwner, {
                    val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, it)
                    autoCompleteTextViewRiskFactor.setAdapter(adapter)
                })
            }
        })
    }

    private fun setupSaveButton() {
        button.setOnClickListener {
            val agentName = textInputLayoutRiskFactor.getString()
            if (agentName.isEmpty()) {
                textInputLayoutRiskFactor.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val agentType = textInputLayoutRiskFactorType.getString()
            val risk = Risk(
                functionId = arguments.function.id,
                agentType = ResourceAgentCategory.fromFormatedValue(agentType),
                agent = agentName,
                generatingSource = textInputLayoutGeneratingSource.getString(),
                intensity = textInputLayoutIntensityConcentration.getString(),
                actionLevel = textInputLayoutActionLevel.getString(),
                tolerance = Tolerance(
                    NR15 = textInputLayoutNR15.getString(),
                    ACGIH = textInputLayoutACGIH.getString(),
                ),
                trajectory = textInputLayoutTrajectory.getString(),
                eliminationNeutralization = textInputLayoutEliminationNeutralization.getString(),
                exposure = textInputLayoutExposureMode.getString(),
                methodology = textInputLayoutSourceMethodology.getString(),
                degreeOfRisk = textInputLayoutDegreeOfRisk.getString(),
                date = Date().format()
            )
            viewModel.save(risk)
            findNavController().popBackStack()
        }
    }

    private fun observeResource(category: ResourceRiskCategory, textView: AutoCompleteTextView) {
        viewModel.getResourceRiskByCategory(category).observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, it)
            textView.setAdapter(adapter)
        })
    }

}