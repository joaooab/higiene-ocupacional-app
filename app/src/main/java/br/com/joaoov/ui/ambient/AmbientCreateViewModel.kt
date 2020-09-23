package br.com.joaoov.ui.ambient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.Ambient
import br.com.joaoov.data.AmbientDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AmbientCreateViewModel(private val ambientDAO: AmbientDAO) : ViewModel() {

    fun salvar(ambient: Ambient) {
        viewModelScope.launch(Dispatchers.IO) {
            ambientDAO.save(ambient)
        }
    }

}
