package br.com.joaoov.ui.settings

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.joaoov.R
import br.com.joaoov.data.State
import br.com.joaoov.ext.getString
import br.com.joaoov.ext.handle
import br.com.joaoov.ext.showToast
import br.com.joaoov.ui.base.BaseBottomSheetDialogFragment
import br.com.joaoov.ui.component.ValidatorEditText
import br.com.joaoov.ui.component.ValidatorEditTextBuilder
import br.com.joaoov.ui.component.ValidatorEditTextType
import kotlinx.android.synthetic.main.bottom_sheet_add_linked_user.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddLinkedUserDialog : BaseBottomSheetDialogFragment(isFullLayout = true) {

    lateinit var onDismiss: () -> Unit
    private val viewModel: AccessKeyViewModel by viewModel()
    private lateinit var validator: ValidatorEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.bottom_sheet_add_linked_user,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validator = ValidatorEditTextBuilder()
            .addField(textInputLayoutEmail, ValidatorEditTextType.Email(isRequired = true))
            .build()
        setupView()
        handleObserver()
    }

    private fun handleObserver() {
        viewModel.linkUser.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Loading -> {
                    buttonAdd.startLoading()
                }
                is State.Success -> {
                    buttonAdd.endLoading()
                    showToast(R.string.message_user_linked)
                }
                is State.Error -> {
                    buttonAdd.endLoading()
                    val message = state.throwable.handle(requireContext(), showToast = false)
                    textInputLayoutEmail.error = message
                }
            }
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss()
    }

    fun setupView() {
        editTextEmail.requestFocus()
        buttonAdd.setOnClickListener {
            val email = textInputLayoutEmail.getString()
            if (validator.validate()) {
                viewModel.linkUser(email)
            }
        }
    }

    companion object {
        fun newInstance(onDismiss: () -> Unit) = AddLinkedUserDialog().apply {
            this.onDismiss = onDismiss
        }
    }

}