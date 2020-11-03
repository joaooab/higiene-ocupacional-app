package br.com.joaoov.ui.ambient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.departament.Departament
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
