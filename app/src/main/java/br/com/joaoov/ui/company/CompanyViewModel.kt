package br.com.joaoov.ui.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.repository.CompanyRepository
import br.com.joaoov.ui.component.move.CompanyDuplicateChain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyViewModel(private val repository: CompanyRepository) : ViewModel() {

    fun getCompanies() = repository.getAll()

    fun save(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(company)
        }
    }

    fun update(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(company)
        }
    }

    fun delete(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(company)
        }
    }

    fun duplicate(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            CompanyDuplicateChain(company).duplicate(parentId = null)
        }
    }

}
