package br.com.joaoov.ui.function

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.repository.FunctionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FunctionViewModel(private val repository: FunctionRepository) : ViewModel() {

    fun getFunctions(ambient: Ambient) = repository.getFunctions(ambient)

    fun salvar(function: Function) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(function)
        }
    }

}
