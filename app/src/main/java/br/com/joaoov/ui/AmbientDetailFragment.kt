package br.com.joaoov.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.joaoov.ComponentesVisuais
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.R
import br.com.joaoov.data.Ambient
import br.com.joaoov.ext.showToast
import kotlinx.android.synthetic.main.fragment_ambient_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class AmbientDetailFragment : Fragment() {

    private val argumentos by navArgs<AmbientDetailFragmentArgs>()
    private val estadoViewModel: EstadoAppViewModel by sharedViewModel()
    private val viewModel: AmbientDetailViewModel by viewModel()
    private lateinit var ambient: Ambient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ambient_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoViewModel.temComponentes = ComponentesVisuais(true)
        ambient = argumentos.ambient
        bindView(ambient)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.action_edit -> true
            R.id.action_delete -> {
                deleteAmbient()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAmbient() {
        viewModel.delete(ambient)
        showToast("Excluido com sucesso")
        navigateToListAmbient()
    }

    private fun navigateToListAmbient() {
        val direction =
            AmbientDetailFragmentDirections.actionAmbientDetailFragmentToAmbientListFragment()
        findNavController().navigate(direction)
    }

    private fun bindView(ambient: Ambient) {
        textViewEmpresa.text = ambient.empresa
        textViewLocal.text = ambient.local
        textViewArea.text = ambient.getArea()
        textViewPeDireito.text = ambient.getPeDireito()
        textViewPiso.text = ambient.piso
        textViewParede.text = ambient.parede
        textViewCobertura.text = ambient.cobertura
        textViewIluminacaoNatural.text = ambient.iluminacaoNatural
        textViewIluminacaoArtificial.text = ambient.iluminacaoArtificial
        textViewVentilacaoNatural.text = ambient.ventilacaoNatural
        textViewVentilacaoArtificial.text = ambient.ventilacaoArtificial
    }
}
