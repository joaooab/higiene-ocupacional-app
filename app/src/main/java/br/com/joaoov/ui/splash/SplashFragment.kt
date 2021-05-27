package br.com.joaoov.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val componentViewModel: ComponentViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = false, toolbar = false)
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            val direction = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            findNavController().navigate(direction)
        }, 3000)
    }

}