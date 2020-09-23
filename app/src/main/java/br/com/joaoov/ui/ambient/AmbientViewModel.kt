package br.com.joaoov.ui.ambient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.joaoov.data.Ambient
import br.com.joaoov.data.AmbientDAO
import br.com.joaoov.data.Company

class AmbientViewModel(private val ambientDAO: AmbientDAO) : ViewModel() {

    private var _ambients = MutableLiveData<List<Ambient>>()

    fun getAmbients(company: Company): LiveData<List<Ambient>> {
        val ambients = ambientDAO.getAllByCompanyID(company.id)
        _ambients.value = ambients.value
        return ambients
    }

    fun exportReport(company: Company) {
        val ambients = _ambients.value ?: listOf()

    }

}
