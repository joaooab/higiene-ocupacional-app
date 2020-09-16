package br.com.joaoov.ui

import androidx.lifecycle.ViewModel
import br.com.joaoov.data.AmbientDAO

class AmbientViewModel(private val ambientDAO: AmbientDAO) : ViewModel() {

    fun getLevantamentos() = ambientDAO.getAll()
}
