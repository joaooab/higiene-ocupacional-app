package br.com.joaoov.ui.risk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.resource.ResourceAgentCategory
import br.com.joaoov.data.local.resource.ResourceRiskCategory
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.ext.format
import br.com.joaoov.repository.ResourceRepository
import br.com.joaoov.repository.RiskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RiskViewModel(
    private val repository: RiskRepository,
    private val resourceRepository: ResourceRepository
) : ViewModel() {

    private val _category = MutableLiveData<ResourceAgentCategory>()
    val category: LiveData<ResourceAgentCategory> = _category

    fun getRisks(function: Function): LiveData<List<Risk>> =
        repository.getAllByFunction(function.id)

    fun changeCategory(category: ResourceAgentCategory) =
        _category.postValue(category)

    fun getResourceRiskByCategory(category: ResourceRiskCategory) =
        resourceRepository.getRiskResourcesByCategory(category)

    fun getResourceAgentByCategory(category: ResourceAgentCategory) =
        resourceRepository.getAgentResourcesByCategory(category)

    fun save(risk: Risk) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(risk)
        }
    }

    fun delete(risk: Risk) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(risk)
        }
    }

    fun update(risk: Risk) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(risk)
        }
    }

    fun duplicate(risk: Risk) {
        val copy = risk.copy(id = 0, date = Date().format())
        save(copy)
    }
}