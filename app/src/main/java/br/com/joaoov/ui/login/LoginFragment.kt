package br.com.joaoov.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonLogin()
    }

    private fun setupButtonLogin() {
        buttonLogin.setOnClickListener {
            buttonLogin.startLoading()
            Handler(Looper.getMainLooper()).postDelayed({
                val direction =
                    LoginFragmentDirections.actionLoginFragmentToCompanyListFragment()
                findNavController().navigate(direction)
            }, 1000)
        }
    }

}