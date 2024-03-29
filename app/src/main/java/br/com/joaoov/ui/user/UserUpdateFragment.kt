package br.com.joaoov.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.Session
import br.com.joaoov.data.State
import br.com.joaoov.data.remote.user.UserPassword
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.handle
import br.com.joaoov.ext.showToast
import br.com.joaoov.ui.component.ValidatorEditText
import br.com.joaoov.ui.component.ValidatorEditTextBuilder
import br.com.joaoov.ui.component.ValidatorEditTextType
import kotlinx.android.synthetic.main.fragment_user_update.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class UserUpdateFragment : Fragment(R.layout.fragment_user_update) {

    private val viewModel: UserViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private lateinit var validator: ValidatorEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = false, toolbar = true, menu = false)
        validator = ValidatorEditTextBuilder()
            .addField(textInputLayoutOldPassword, ValidatorEditTextType.Password)
            .addField(textInputLayoutPassword, ValidatorEditTextType.Password)
            .addField(textInputLayoutPasswordConfirm, ValidatorEditTextType.PasswordConfirm(textInputLayoutPassword))
            .build()

        setupView()
        handleObserver()
    }

    private fun handleObserver() {
        viewModel.stateUser.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    buttonRegister.startLoading()
                }
                is State.Success -> {
                    buttonRegister.endLoading()
                    requireContext().showToast(R.string.message_user_updated_password)
                }
                is State.Error -> {
                    buttonRegister.endLoading()
                    state.throwable.handle(requireContext())
                }
            }
        }
    }

    private fun setupView() {
        buttonRegister.setOnClickListener {
            if (validator.validate()) {
                val username = Session.user?.username.orEmpty()
                val oldPassword = textInputLayoutOldPassword.getString()
                val password = textInputLayoutPassword.getString()

                viewModel.updatePassword(UserPassword(username, password, oldPassword))
            }
        }
    }

}