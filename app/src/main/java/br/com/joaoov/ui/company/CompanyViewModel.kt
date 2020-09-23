package br.com.joaoov.ui.company

import androidx.lifecycle.ViewModel
import br.com.joaoov.data.CompanyDAO

class CompanyViewModel(private val companyDAO: CompanyDAO) : ViewModel() {

    fun getCompanies() = companyDAO.getAll()

}
