package br.com.joaoov

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.joaoov.data.PathState

class MainViewModel : ViewModel() {

    private val _pathState = MutableLiveData<PathState>()
    val pathState: LiveData<PathState> = _pathState

    fun addPath(path: Path) {
        _pathState.postValue(PathState.Add(path))
    }

    fun removePath() {
        _pathState.postValue(PathState.Remove)
    }

}