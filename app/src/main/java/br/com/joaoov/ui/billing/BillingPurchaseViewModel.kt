package br.com.joaoov.ui.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.usecase.CheckBillingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BillingPurchaseViewModel(private val checkBillingUseCase: CheckBillingUseCase) : ViewModel() {

    private val _billingState = MutableStateFlow<Boolean>(true)
    val billingState: StateFlow<Boolean> = _billingState

    fun checkPayment() {
        viewModelScope.launch(Dispatchers.IO) {
            _billingState.value = checkBillingUseCase()
        }
    }

}
