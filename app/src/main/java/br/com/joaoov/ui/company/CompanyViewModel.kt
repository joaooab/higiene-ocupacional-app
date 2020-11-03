package br.com.joaoov.ui.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.company.CompanyDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyViewModel(private val companyDAO: CompanyDAO) : ViewModel() {

    fun getCompanies() = companyDAO.getAll()

    fun salvar(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            companyDAO.save(company)
        }
    }

    fun delete(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            companyDAO.delete(company)
        }
    }

}
