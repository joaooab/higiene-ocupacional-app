package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.Ambient
import br.com.joaoov.ext.*
import br.com.joaoov.ui.NoFilterAdapter
import kotlinx.android.synthetic.main.fragment_ambient_create.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AmbientCreateFragment : Fragment(R.layout.fragment_ambient_create) {

    private val arguments by navArgs<AmbientCreateFragmentArgs>()
    private val viewModel: AmbientViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFloor()
        setupWall()
        setupCoverage()
        setupNaturalLighting()
        setupArtificialLighting()
        setupNaturalVentilation()
        setupArtificialVentilation()
        setupSaveButton()
    }

    private fun setupSaveButton() {
        hideKeyboard()
        buttonSave.setOnClickListener {
            val localName = textInputLayoutLocal.getString()
            if (localName.isEmpty()) {
                textInputLayoutLocal.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val ambient = Ambient(
                departamentId = arguments.departament.id,
                local = localName,
                date = Date().format(),
                width = textInputLayoutAreaWidth.getDouble(),
                length = textInputLayoutAreaLenght.getDouble(),
                height = textInputLayoutHeight.getDouble(),
                floor = textInputLayoutFloor.getString(),
                wall = textInputLayoutWall.getString(),
                coverage = textInputLayoutCoverage.getString(),
                naturalLighting = textInputLayoutNaturalLighting.getString(),
                artificialLighting = textInputLayoutArtificialLighting.getString(),
                naturalVentilation = textInputLayoutNaturalVentilation.getString(),
                artificialVentilation = textInputLayoutArtificialVentilation.getString()
            )
            viewModel.salvar(ambient)
            showToast("Criado com sucesso")
            findNavController().popBackStack()
        }
    }

    private fun setupArtificialVentilation() {
        configurarSpinner(
            autoCompleteTextViewArtificialVentilation,
            R.array.ventilacao_artificial_array
        )
    }

    private fun setupNaturalVentilation() {
        configurarSpinner(autoCompleteTextViewNaturalVentilation, R.array.ventilacao_natural_array)
    }

    private fun setupArtificialLighting() {
        configurarSpinner(
            autoCompleteTextViewArtificialLighting,
            R.array.iluminacao_artificial_array
        )

    }

    private fun setupNaturalLighting() {
        configurarSpinner(autoCompleteTextViewNaturalLighting, R.array.iluminacao_natural_array)
    }

    private fun setupFloor() {
        configurarSpinner(autoCompleteTextViewFloor, R.array.piso_array)
    }

    private fun setupWall() {
        configurarSpinner(autoCompleteTextViewWall, R.array.parede_array)
    }

    private fun setupCoverage() {
        configurarSpinner(autoCompleteTextViewCoverage, R.array.cobertura_array)
    }

    private fun configurarSpinner(textView: AutoCompleteTextView, array: Int) {
        textView.setAdapter(
            NoFilterAdapter(
                requireContext(),
                R.layout.drop_down_item,
                resources.getStringArray(array)
            )
        )
    }

}
