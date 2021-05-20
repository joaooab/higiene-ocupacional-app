package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.resource.ResourceAmbientCategory
import br.com.joaoov.ext.*
import br.com.joaoov.ui.component.AreaDialog
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_ambient_create.*
import kotlinx.android.synthetic.main.include_ambient_form.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AmbientCreateFragment : Fragment(R.layout.fragment_ambient_create) {

    private val arguments by navArgs<AmbientCreateFragmentArgs>()
    private val viewModel: AmbientViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = true)
        setupView()
        handleObserve()
    }

    private fun setupView() {
        setupSaveButton()
        setupCalcButton()
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

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val localName = textInputLayoutLocal.getString()
            if (localName.isEmpty()) {
                textInputLayoutLocal.error = getString(R.string.message_error_required)
                return@setOnClickListener
            }
            val ambient = Ambient(
                departamentId = arguments.departament.id,
                name = localName,
                date = Date().format(),
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
            viewModel.save(ambient)
            findNavController().popBackStack()
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
