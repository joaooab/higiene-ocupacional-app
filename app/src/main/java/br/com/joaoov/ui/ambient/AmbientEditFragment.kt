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
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.hideKeyboard
import br.com.joaoov.ext.setString
import br.com.joaoov.ext.supportFragmentManager
import br.com.joaoov.ui.component.AreaDialog
import kotlinx.android.synthetic.main.fragment_ambient_edit.*
import org.koin.android.viewmodel.ext.android.viewModel

class AmbientEditFragment : Fragment(R.layout.fragment_ambient_edit) {

    private val arguments by navArgs<AmbientEditFragmentArgs>()
    private val viewModel: AmbientViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            textInputLayoutRoofTiles.setString(it.ceiling)
            textInputLayoutWindow.setString(it.window)
            textInputLayoutCeiling.setString(it.ceiling)
            textInputLayoutNaturalLighting.setString(it.naturalLighting)
            textInputLayoutArtificialLighting.setString(it.artificialLighting)
            textInputLayoutNaturalVentilation.setString(it.naturalVentilation)
            textInputLayoutArtificialVentilation.setString(it.artificialVentilation)
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
                roofTiles = textInputLayoutRoof.getString(),
                window = textInputLayoutWindow.getString(),
                ceiling = textInputLayoutCeiling.getString(),
                naturalLighting = textInputLayoutNaturalLighting.getString(),
                artificialLighting = textInputLayoutArtificialLighting.getString(),
                naturalVentilation = textInputLayoutNaturalVentilation.getString(),
                artificialVentilation = textInputLayoutArtificialVentilation.getString()
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

}
