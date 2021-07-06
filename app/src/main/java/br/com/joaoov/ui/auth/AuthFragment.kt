package br.com.joaoov.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.SyncViewModel
import br.com.joaoov.data.State
import br.com.joaoov.data.remote.auth.Auth
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.handle
import br.com.joaoov.ui.component.ValidatorEditText
import br.com.joaoov.ui.component.ValidatorEditTextBuilder
import br.com.joaoov.ui.component.ValidatorEditTextType
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private val syncViewModel: SyncViewModel by sharedViewModel()
    private val viewModel by viewModel<AuthViewModel>()
    private lateinit var validator: ValidatorEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = false, toolbar = false, menu = false)
        validator = ValidatorEditTextBuilder()
            .addField(textInputLayoutAuthEmail, ValidatorEditTextType.Email(isRequired = true))
            .addField(textInputLayoutAuthPassword, ValidatorEditTextType.Password)
            .build()

        setupView()
        handleObserver()
    }

    private fun setupView() {
        buttonLogin.setOnClickListener {
            if (validator.validate()) {
                val email = textInputLayoutAuthEmail.getString()
                val password = textInputLayoutAuthPassword.getString()

                viewModel.auth(Auth(email, password))
            }
        }

        textViewRegister.setOnClickListener {
            val direction = AuthFragmentDirections.actionAuthFragmentToUserFragment()
            findNavController().navigate(direction)
        }

        textViewForgotPassword.setOnClickListener {
            val direction = AuthFragmentDirections.actionAuthFragmentToForgotPasswordFragment()
            findNavController().navigate(direction)
        }
    }

    private fun handleObserver() {
        viewModel.stateAuth.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    buttonLogin.startLoading()
                }
                is State.Success -> {
                    buttonLogin.endLoading()
                    navigateToHome()
                    syncViewModel.syncronize()
                }
                is State.Error -> {
                    buttonLogin.endLoading()
                    state.throwable.handle(requireContext())
                }
            }
        }
    }

    private fun navigateToHome() {
        val direction = AuthFragmentDirections.actionAuthFragmentToCompanyListFragment()
        findNavController().navigate(direction)
    }

}