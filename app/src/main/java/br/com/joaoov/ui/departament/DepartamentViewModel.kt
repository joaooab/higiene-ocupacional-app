package br.com.joaoov.ui.departament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.repository.DepartamentRepository
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.departament.Departament
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepartamentViewModel(private val repository: DepartamentRepository) : ViewModel() {

    fun getDepartaments(company: Company) = repository.getDepartaments(company)

    fun salvar(departament: Departament) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(departament)
        }
    }

}
