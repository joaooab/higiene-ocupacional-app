package br.com.joaoov.ui.risk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import br.com.joaoov.R
import kotlinx.android.synthetic.main.fragment_risk_create.*
import org.koin.android.viewmodel.ext.android.viewModel

class RiskCreateFragment: Fragment(R.layout.fragment_risk_create) {

    private val mViewModel: RiskViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {

        }
    }
}