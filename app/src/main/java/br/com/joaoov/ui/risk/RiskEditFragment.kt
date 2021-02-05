package br.com.joaoov.ui.risk

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.local.resource.ResourceAgentCategory
import br.com.joaoov.data.local.resource.ResourceRiskCategory
import br.com.joaoov.data.local.resource.getFormatedValue
import br.com.joaoov.data.local.resource.isOld
import br.com.joaoov.data.local.risk.Tolerance
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.setString
import kotlinx.android.synthetic.main.fragment_risk_edit.*
import kotlinx.android.synthetic.main.include_risk_form.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RiskEditFragment : Fragment(R.layout.fragment_risk_edit) {

    private val arguments by navArgs<RiskEditFragmentArgs>()
    private val viewModel: RiskViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = true)
        setupView()
        handleObserve()
        setupSaveButton()
    }

    private fun setupView() {
        arguments.risk.let {
            if (it.agentCategory.isOld()) {
                radioButtonOld.isChecked = true
            }
            textInputLayoutRiskFactorType.setString(it.agentCategory.getFormatedValue())
            textInputLayoutRiskFactor.setString(it.agent)
            textInputLayoutGeneratingSource.setString(it.generatedSource)
            textInputLayoutIntensityConcentration.setString(it.intensity)
            textInputLayoutActionLevel.setString(it.actionLevel)
            textInputLayoutNR15.setString(it.tolerance.NR15)
            textInputLayoutACGIH.setString(it.tolerance.ACGIH)
            textInputLayoutTrajectory.setString(it.trajectory)
            textInputLayoutEliminationNeutralization.setString(it.eliminationNeutralization)
            textInputLayoutExposureMode.setString(it.exposure)
            textInputLayoutSourceMethodology.setString(it.methodology)
            textInputLayoutDegreeOfRisk.setString(it.degreeOfRisk)
        }

        arguments.risk.let {
            if (it.agentCategory.isOld()) {
                setupAgents(ResourceAgentCategory.getFormatedValues(isESocial = false))
            } else {
                setupAgents(ResourceAgentCategory.getFormatedValues(isESocial = true))
            }
        }
        setupRadioButton()
        setupSaveButton()
    }

    private fun setupRadioButton() {
        radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.radioButtonESocial -> {
                    setupAgents(ResourceAgentCategory.getFormatedValues(isESocial = true))
                }
                R.id.radioButtonOld -> {
                    setupAgents(ResourceAgentCategory.getFormatedValues(isESocial = false))
                }
            }
        }
    }

    private fun setupAgents(categories: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.drop_down_item,
            categories
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
            if (category != null && category != ResourceAgentCategory.UNKNOWN) {
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
        buttonEdit.setOnClickListener {
            val agentName = textInputLayoutRiskFactor.getString()
            if (agentName.isEmpty()) {
                textInputLayoutRiskFactor.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val agentType = textInputLayoutRiskFactorType.getString()
            val risk = arguments.risk.copy(
                agentCategory = ResourceAgentCategory.fromFormatedValue(agentType),
                agent = agentName,
                generatedSource = textInputLayoutGeneratingSource.getString(),
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
            )
            viewModel.update(risk)
            findNavController().popBackStack()
        }
    }

    private fun observeResource(category: ResourceRiskCategory, textView: AutoCompleteTextView) {
        viewModel.getResourceRiskByCategory(category).observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, it)
            textView.setAdapter(adapter)
        })
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

}