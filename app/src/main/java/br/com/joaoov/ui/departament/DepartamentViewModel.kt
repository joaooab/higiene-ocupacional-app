package br.com.joaoov.ui.departament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.repository.DepartamentRepository
import br.com.joaoov.ui.component.move.DepartamentDuplicateChain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepartamentViewModel(private val repository: DepartamentRepository) : ViewModel() {

    fun getDepartaments(company: Company) = repository.getAllByCompany(company)

    fun salvar(departament: Departament) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(departament)
        }
    }

    fun update(departament: Departament) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(departament)
        }
    }

    fun delete(departament: Departament) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(departament)
        }
    }

    fun duplicate(departament: Departament) {
        viewModelScope.launch(Dispatchers.IO) {
            DepartamentDuplicateChain(departament).duplicate(parentId = null)
        }
    }


}
