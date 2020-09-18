package br.com.joaoov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.ComponentesVisuais
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.R
import br.com.joaoov.data.Ambient
import br.com.joaoov.ext.*
import kotlinx.android.synthetic.main.fragment_ambient_create.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AmbientCreateFragment : Fragment() {

    private val viewModel: AmbientCreateViewModel by viewModel()
    private val estadoViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ambient_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoViewModel.temComponentes = ComponentesVisuais(false)
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
                empresa = editTextEmpresa.getString(),
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
