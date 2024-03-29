package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.local.resource.ResourceAmbientCategory
import br.com.joaoov.ext.*
import br.com.joaoov.ui.component.AreaDialog
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_ambient_edit.*
import kotlinx.android.synthetic.main.include_ambient_form.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AmbientEditFragment : Fragment(R.layout.fragment_ambient_edit) {

    private val arguments by navArgs<AmbientEditFragmentArgs>()
    private val viewModel: AmbientViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = true)
        setupView()
        handleObserve()
    }

    private fun setupView() {
        arguments.ambient.let {
            textInputLayoutLocal.setString(it.name)
            textInputLayoutArea.setString(it.area)
            textInputLayoutHeight.setString(it.height)
            textInputLayoutFloor.setString(it.floor)
            textInputLayoutWall.setString(it.wall)
            textInputLayoutRoof.setString(it.roof)
            textInputLayoutRoofTiles.setString(it.roofTiles)
            textInputLayoutWindow.setString(it.window)
            textInputLayoutCeiling.setString(it.ceiling)
            textInputLayoutNaturalLighting.setString(it.naturalLighting)
            textInputLayoutArtificialLighting.setString(it.artificialLighting)
            textInputLayoutNaturalVentilation.setString(it.naturalVentilation)
            textInputLayoutArtificialVentilation.setString(it.artificialVentilation)
            textInputLayoutStructure.setString(it.structure)
        }

        setupSaveButton()
        setupCalcButton()
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
                area = textInputLayoutArea.getString(),
                height = textInputLayoutHeight.getString(),
                floor = textInputLayoutFloor.getString(),
                wall = textInputLayoutWall.getString(),
                roof = textInputLayoutRoof.getString(),
                roofTiles = textInputLayoutRoofTiles.getString(),
                window = textInputLayoutWindow.getString(),
                ceiling = textInputLayoutCeiling.getString(),
                naturalLighting = textInputLayoutNaturalLighting.getString(),
                artificialLighting = textInputLayoutArtificialLighting.getString(),
                naturalVentilation = textInputLayoutNaturalVentilation.getString(),
                artificialVentilation = textInputLayoutArtificialVentilation.getString(),
                structure = textInputLayoutStructure.getString()
            )
            viewModel.update(ambient)
            findNavController().popBackStack()
        }
    }

    private fun setupCalcButton() {
        calcButton.setOnClickListener {
            supportFragmentManager {
                AreaDialog.newInstance {
                    textInputLayoutArea.setString(it)
                }.show(this, "")
            }
        }
    }

    private fun handleObserve() {
        observeResource(
            ResourceAmbientCategory.FLOOR,
            textInputLayoutFloor
        )
        observeResource(
            ResourceAmbientCategory.ROOF,
            textInputLayoutRoof
        )
        observeResource(
            ResourceAmbientCategory.ROOF_TILES,
            textInputLayoutRoofTiles
        )
        observeResource(
            ResourceAmbientCategory.NATURAL_LIGHTING,
            textInputLayoutNaturalLighting
        )
        observeResource(
            ResourceAmbientCategory.NATURAL_VENTILATION,
            textInputLayoutNaturalVentilation
        )
        observeResource(
            ResourceAmbientCategory.ARTIFICIAL_LIGHTING,
            textInputLayoutArtificialLighting
        )
        observeResource(
            ResourceAmbientCategory.ARTIFICIAL_VENTILATION,
            textInputLayoutArtificialVentilation
        )
        observeResource(
            ResourceAmbientCategory.WALL,
            textInputLayoutWall
        )
        observeResource(
            ResourceAmbientCategory.WINDOWN,
            textInputLayoutWindow
        )
        observeResource(
            ResourceAmbientCategory.CEILING,
            textInputLayoutCeiling
        )
        observeResource(
            ResourceAmbientCategory.STRUCTURE,
            textInputLayoutStructure
        )
    }

    private fun observeResource(category: ResourceAmbientCategory, inputLayout: TextInputLayout) {
        viewModel.getResourceByCategory(category).observe(viewLifecycleOwner, { resources ->
            inputLayout.setupData(resources)
        })
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

}
