package br.com.joaoov.ui.component

import br.com.joaoov.ext.*
import com.google.android.material.textfield.TextInputLayout

data class ValidatorEditTextField(
    val field: TextInputLayout,
    val errorMessage: String,
    val onValidation: () -> Boolean
)

sealed class ValidatorEditTextType {
    object Requiered : ValidatorEditTextType()
    class Email(val isRequired: Boolean) : ValidatorEditTextType()
    class CNPJ(val isRequired: Boolean) : ValidatorEditTextType()
    object Password : ValidatorEditTextType()
    class PasswordConfirm(val passwordField: TextInputLayout) : ValidatorEditTextType()
}

class ValidatorEditTextBuilder {

    private val validators = mutableListOf<ValidatorEditTextField>()

    fun addField(
        field: TextInputLayout,
        type: ValidatorEditTextType
    ): ValidatorEditTextBuilder {
        when (type) {
            is ValidatorEditTextType.Requiered -> setRequired(field)
            is ValidatorEditTextType.Email -> setEmail(field, type.isRequired)
            is ValidatorEditTextType.CNPJ -> setCNPJ(field, type.isRequired)
            is ValidatorEditTextType.Password -> setPassword(field)
            is ValidatorEditTextType.PasswordConfirm -> setPasswordConfirm(
                field,
                type.passwordField
            )
        }

        return this
    }

    private fun setRequired(field: TextInputLayout) {
        addField(field, FIELD_ERROR_REQUIRED) { field.getString().isNotEmpty() }
        field.setTypeRequired()
    }

    private fun setEmail(field: TextInputLayout, required: Boolean) {
        addField(field, FIELD_ERROR_EMAIL) {
            val text = field.getString()
            if (required) {
                text.isValidEmail()
            } else {
                text.isEmpty() || text.isValidEmail()
            }
        }

        field.setTypeEmail(required)
    }

    private fun setCNPJ(field: TextInputLayout, required: Boolean) {
        addField(field, FIELD_ERROR_CNPJ) {
            val text = field.getString()
            if (required) {
                text.isValidCNPJ()
            } else {
                text.isEmpty() || text.isValidCNPJ()
            }
        }

        field.setTypeCNPJ(required)
    }

    private fun setPassword(field: TextInputLayout) {
        addField(field, FIELD_ERROR_PASSWORD) {
            field.getString().isValidPassword()
        }

        field.setTypePassword()
    }

    private fun setPasswordConfirm(field: TextInputLayout, passwordField: TextInputLayout) {
        addField(field, FIELD_ERROR_PASSWORD_CONFIRM) {
            val confirmPassword = field.getString()
            confirmPassword.isValidPassword() && confirmPassword == passwordField.getString()
        }

        field.setTypePasswordConfirm(passwordField)
    }

    private fun addField(
        field: TextInputLayout,
        errorMessage: String = "",
        onValidation: () -> Boolean
    ) {
        val validator = ValidatorEditTextField(field, errorMessage, onValidation)
        validators.add(validator)
    }

    fun build(): ValidatorEditText {
        return ValidatorEditText(validators)
    }


}

class ValidatorEditText(private val validators: List<ValidatorEditTextField>) {
    fun validate(): Boolean {
        var success = true
        for (validator in validators) {
            if (!validator.onValidation.invoke()) {
                validator.field.error = validator.errorMessage
            }
            if (hasError(validator)) {
                success = false
            }
        }

        return success
    }

    private fun hasError(validatorEditText: ValidatorEditTextField) =
        !validatorEditText.field.error.isNullOrEmpty()
}