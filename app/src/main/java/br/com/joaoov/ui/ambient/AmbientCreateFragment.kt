package br.com.joaoov.ui.ambient

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentesVisuais
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.R
import br.com.joaoov.data.Ambient
import br.com.joaoov.ext.*
import br.com.joaoov.ui.NoFilterAdapter
import kotlinx.android.synthetic.main.fragment_ambient_create.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AmbientCreateFragment : Fragment(R.layout.fragment_ambient_create) {

    private val arguments by navArgs<AmbientCreateFragmentArgs>()
    private val viewModel: AmbientCreateViewModel by viewModel()
    private val estadoViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoViewModel.temComponentes = ComponentesVisuais(true)
        configurarSpinnerPiso()
        configurarSpinnerParede()
        configurarSpinnerCobertura()
        configurarSpinnerIlumnicaoNatural()
        configurarSpinnerIlumnicaoArtificial()
        configurarSpinnerVentilacaoNatural()
        configurarSpinnerVentilacaoArtificial()
        configurarBotaoSalvar()
    }

    private fun configurarBotaoSalvar() {
        hideKeyboard()
        buttonSalvar.setOnClickListener {
            val ambient = Ambient(
                companyID = arguments.company.id,
                local = editTextLocal.getString(),
                data = Date().format(),
                areaLargura = editTextAreaLargura.getDouble(),
                areaComprimento = editTextAreaComprimento.getDouble(),
                peDireito = editTextPeDireito.getDouble(),
                piso = textViewSpinerPiso.getString(),
                parede = textViewSpinerParede.getString(),
                cobertura = textViewSpinerCobertura.getString(),
                iluminacaoNatural = textViewSpinerIluminacaoNatural.getString(),
                iluminacaoArtificial = textViewSpinerIluminacaoArtificial.getString(),
                ventilacaoNatural = textViewSpinerVentilacaoNatural.getString(),
                ventilacaoArtificial = textViewSpinerVentilacaoArtificial.getString()
            )
            viewModel.salvar(ambient)
            showToast("Criado com sucesso")
            findNavController().popBackStack()
        }
    }

    private fun configurarSpinnerVentilacaoArtificial() {
        configurarSpinner(textViewSpinerVentilacaoArtificial, R.array.ventilacao_artificial_array)
    }

    private fun configurarSpinnerVentilacaoNatural() {
        configurarSpinner(textViewSpinerVentilacaoNatural, R.array.ventilacao_natural_array)
    }

    private fun configurarSpinnerIlumnicaoArtificial() {
        configurarSpinner(textViewSpinerIluminacaoArtificial, R.array.iluminacao_artificial_array)

    }

    private fun configurarSpinnerIlumnicaoNatural() {
        configurarSpinner(textViewSpinerIluminacaoNatural, R.array.iluminacao_natural_array)
    }

    private fun configurarSpinnerPiso() {
        configurarSpinner(textViewSpinerPiso, R.array.piso_array)
    }

    private fun configurarSpinnerParede() {
        configurarSpinner(textViewSpinerParede, R.array.parede_array)
    }

    private fun configurarSpinnerCobertura() {
        configurarSpinner(textViewSpinerCobertura, R.array.cobertura_array)
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
