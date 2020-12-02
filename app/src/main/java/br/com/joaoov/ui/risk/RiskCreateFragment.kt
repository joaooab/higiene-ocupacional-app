package br.com.joaoov.ui.risk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.joaoov.R
import kotlinx.android.synthetic.main.fragment_risk_create.*
import org.koin.android.viewmodel.ext.android.viewModel

class RiskCreateFragment : Fragment(R.layout.fragment_risk_create) {

    private val mViewModel: RiskViewModel by viewModel()
    private val arguments by navArgs<RiskCreateFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments.agent.split(".")[0]
        mViewModel.getAgents(category).observe(viewLifecycleOwner, {
            Log.e("teste", "teste")
        })

        button.setOnClickListener {

        }
    }
}