package br.com.joaoov.ui.ambient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.Ambient
import br.com.joaoov.data.AmbientDAO
import br.com.joaoov.data.Company
import br.com.joaoov.data.Departament
import br.com.joaoov.repository.AmbientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AmbientViewModel(private val repository: AmbientRepository) : ViewModel() {

    fun getAmbients(departament: Departament): LiveData<List<Ambient>> =
        repository.getAmbients(departament)


    fun salvar(ambient: Ambient) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(ambient)
        }
    }

    fun delete(ambient: Ambient) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(ambient)
        }
    }

}
