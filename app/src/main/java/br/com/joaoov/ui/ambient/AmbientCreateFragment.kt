package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
        handleObserve()
        setupView()
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
        viewModel.getResourceByCategory(category).observe(viewLifecycleOwner, { resources ->
            val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, resources)
            textView.setAdapter(adapter)
        })
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
                artificialVentilation = textInputLayoutArtificialVentilation.getString()
            )
            viewModel.save(ambient)
            findNavController().popBackStack()
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

}
