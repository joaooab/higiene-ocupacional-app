package br.com.joaoov

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.joaoov.data.PathState

class ComponentViewModel : ViewModel() {

    private val _components = MutableLiveData<Components>().also {
        it.value = withComponents
    }
    val components: LiveData<Components> = _components

    var withComponents = Components()
        set(value) {
            field = value
            _components.value = value
        }

    private val _pathState = MutableLiveData<PathState>()
    val pathState: LiveData<PathState> = _pathState

    fun addPath(path: Path) {
        _pathState.postValue(PathState.Add(path))
    }

    fun removePath() {
        _pathState.postValue(PathState.Remove)
    }

}

data class Components(
    val path: Boolean = true,
    val toolbar: Boolean = true
)