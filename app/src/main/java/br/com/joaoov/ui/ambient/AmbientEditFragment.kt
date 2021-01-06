package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import br.com.joaoov.data.local.resource.ResourceAmbientCategory
import br.com.joaoov.ext.*
import br.com.joaoov.ui.NoFilterAdapter
import kotlinx.android.synthetic.main.fragment_ambient_create.*
import org.koin.android.viewmodel.ext.android.viewModel

class AmbientEditFragment : Fragment(R.layout.fragment_ambient_edit) {

    private val arguments by navArgs<AmbientEditFragmentArgs>()
    private val viewModel: AmbientViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        handleObserve()
        setupSaveButton()
    }

    private fun setupView() {
        arguments.ambient.let {
            textInputLayoutLocal.setString(it.name)
            textInputLayoutAreaWidth.setString(it.width.toString())
            textInputLayoutAreaLenght.setString(it.length.toString())
            textInputLayoutHeight.setString(it.height.toString())
            textInputLayoutFloor.setString(it.floor)
            textInputLayoutWall.setString(it.wall)
            textInputLayoutRoof.setString(it.roof)
            textInputLayoutRoofTiles.setString(it.ceiling)
            textInputLayoutWindow.setString(it.window)
            textInputLayoutCeiling.setString(it.ceiling)
            textInputLayoutNaturalLighting.setString(it.naturalLighting)
            textInputLayoutArtificialLighting.setString(it.artificialLighting)
            textInputLayoutNaturalVentilation.setString(it.naturalVentilation)
            textInputLayoutArtificialVentilation.setString(it.artificialVentilation)
        }
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val localName = textInputLayoutLocal.getString()
            if (localName.isEmpty()) {
                textInputLayoutLocal.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val ambient = arguments.ambient.copy(
                name = localName,
                width = textInputLayoutAreaWidth.getDouble(),
                length = textInputLayoutAreaLenght.getDouble(),
                height = textInputLayoutHeight.getDouble(),
                floor = textInputLayoutFloor.getString(),
                wall = textInputLayoutWall.getString(),
                roof = textInputLayoutRoof.getString(),
                roofTiles = textInputLayoutRoof.getString(),
                window = textInputLayoutWindow.getString(),
                ceiling = textInputLayoutCeiling.getString(),
                naturalLighting = textInputLayoutNaturalLighting.getString(),
                artificialLighting = textInputLayoutArtificialLighting.getString(),
                naturalVentilation = textInputLayoutNaturalVentilation.getString(),
                artificialVentilation = textInputLayoutArtificialVentilation.getString()
            )
            viewModel.salvar(ambient)
            showToast(R.string.message_success_edited)
            findNavController().popBackStack()
        }
    }

    private fun handleObserve() {
        observeResource(
            ResourceAmbientCategory.FLOOR,
            autoCompleteTextViewFloor
        )
        observeResource(
            ResourceAmbientCategory.ROOF,
            autoCompleteTextViewRoof
        )
        observeResource(
            ResourceAmbientCategory.ROOF_TILES,
            autoCompleteTextViewRoofTiles
        )
        observeResource(
            ResourceAmbientCategory.NATURAL_LIGHTING,
            autoCompleteTextViewNaturalLighting
        )
        observeResource(
            ResourceAmbientCategory.NATURAL_VENTILATION,
            autoCompleteTextViewNaturalVentilation
        )
        observeResource(
            ResourceAmbientCategory.ARTIFICIAL_LIGHTING,
            autoCompleteTextViewArtificialLighting
        )
        observeResource(
            ResourceAmbientCategory.ARTIFICIAL_VENTILATION,
            autoCompleteTextViewArtificialVentilation
        )
        observeResource(
            ResourceAmbientCategory.WALL,
            autoCompleteTextViewWall
        )
        observeResource(
            ResourceAmbientCategory.WINDOWN,
            autoCompleteTextViewWindow
        )
        observeResource(
            ResourceAmbientCategory.CEILING,
            autoCompleteTextViewCeiling
        )
    }

    private fun observeResource(category: ResourceAmbientCategory, textView: AutoCompleteTextView) {
        viewModel.getResourceByCategory(category).observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, it)
            textView.setAdapter(adapter)
        })
    }


    override fun onPause() {
        super.onPause()
        hideKeyboard()
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
        configurarSpinner(autoCompleteTextViewRoof, R.array.cobertura_array)
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
