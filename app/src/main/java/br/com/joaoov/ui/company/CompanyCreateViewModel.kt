package br.com.joaoov.ui.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.Ambient
import br.com.joaoov.data.AmbientDAO
import br.com.joaoov.data.Company
import br.com.joaoov.data.CompanyDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyCreateViewModel(private val companyDAO: CompanyDAO) : ViewModel() {

    fun salvar(company: Company) {
        viewModelScope.launch(Dispatchers.IO) {
            companyDAO.save(company)
        }
    }

}
