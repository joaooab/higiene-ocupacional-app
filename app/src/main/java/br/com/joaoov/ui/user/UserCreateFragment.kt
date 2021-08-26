package br.com.joaoov.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.joaoov.ComponentViewModel
import br.com.joaoov.Components
import br.com.joaoov.R
import br.com.joaoov.data.State
import br.com.joaoov.data.remote.user.UserCreate
import br.com.joaoov.ext.*
import br.com.joaoov.ui.component.ValidatorEditText
import br.com.joaoov.ui.component.ValidatorEditTextBuilder
import br.com.joaoov.ui.component.ValidatorEditTextType
import kotlinx.android.synthetic.main.fragment_user_create.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class UserCreateFragment : Fragment(R.layout.fragment_user_create) {

    private val viewModel: UserViewModel by viewModel()
    private val componentViewModel: ComponentViewModel by sharedViewModel()
    private lateinit var userValidator: ValidatorEditText
    private lateinit var legalUserValidator: ValidatorEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentViewModel.withComponents = Components(path = false, toolbar = true, menu = false)
        userValidator = ValidatorEditTextBuilder()
            .addField(textInputLayoutEmail, ValidatorEditTextType.Email(isRequired = true))
            .addField(textInputLayoutName, ValidatorEditTextType.Requiered)
            .addField(textInputLayoutPassword, ValidatorEditTextType.Password)
            .addField(
                textInputLayoutPasswordConfirm,
                ValidatorEditTextType.PasswordConfirm(textInputLayoutPassword)
            )
            .build()

        legalUserValidator = ValidatorEditTextBuilder()
            .addField(textInputLayoutDocument, ValidatorEditTextType.CNPJ(isRequired = true))
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
                    requireContext().showToast(R.string.message_user_created)
                }
                is State.Error -> {
                    buttonRegister.endLoading()
                    state.throwable.handle(requireContext())
                }
            }
        }
    }

    private fun setupView() {
        setupGroupUserType()
        setupButtonRegister()
    }

    private fun setupGroupUserType() {
        radioGroupUserType.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioButtomPhysicalUser) {
                textInputLayoutDocument.gone()
            } else {
                textInputLayoutDocument.show()
            }
        }
    }

    private fun setupButtonRegister() {
        buttonRegister.setOnClickListener {
            if (formValidate()) {
                val email = textInputLayoutEmail.getString()
                val name = textInputLayoutName.getString()
                val password = textInputLayoutPassword.getString()
                val document = if (isPhysicalUserForm()) {
                    null
                } else {
                    textInputLayoutDocument.getStringOrNull()
                }
                val userCreate = UserCreate(email, name, password, document)

                viewModel.create(userCreate)
            }
        }
    }

    private fun formValidate(): Boolean {
        return if (isPhysicalUserForm()) {
            userValidator.validate()
        } else {
            val userValidate = userValidator.validate()
            val legalUserValidate = legalUserValidator.validate()

            userValidate && legalUserValidate
        }
    }

    private fun isPhysicalUserForm() =
        radioGroupUserType.checkedRadioButtonId == R.id.radioButtomPhysicalUser

}