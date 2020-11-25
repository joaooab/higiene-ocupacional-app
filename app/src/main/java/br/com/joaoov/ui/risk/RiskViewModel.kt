package br.com.joaoov.ui.risk

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.repository.RiskRepository

class RiskViewModel(private val repository: RiskRepository): ViewModel() {

    fun getRisks(function: Function): LiveData<List<Risk>> = repository.getRisks(function.id)
}