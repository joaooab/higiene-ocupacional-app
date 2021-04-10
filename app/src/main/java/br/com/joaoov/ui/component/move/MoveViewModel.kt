package br.com.joaoov.ui.component.move

import androidx.lifecycle.*
import br.com.joaoov.data.State
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.repository.AmbientRepository
import br.com.joaoov.repository.DepartamentRepository
import br.com.joaoov.repository.FunctionRepository
import kotlinx.coroutines.launch

class MoveViewModel(
    private val departamentRepository: DepartamentRepository,
    private val ambientRepository: AmbientRepository,
    private val functionRepository: FunctionRepository
) : ViewModel() {

    private val _moveState = MutableLiveData<State<Unit>>()
    val moveState: LiveData<State<Unit>> = _moveState

    private val _selectedItemId = MutableLiveData<Any?>()
    val selectedItem: LiveData<Any?> = _selectedItemId

    fun loadPossibleMoves(move: Any): LiveData<List<Any>> {
        return when (move) {
            is Ambient -> {
                val department = departamentRepository.getById(move.departamentId)
                departamentRepository.getAllByCompany(Company(department.companyId))
                    .map { departments ->
                        departments.filter { it.id != department.id }
                    }
            }
            is Function -> {
                val ambient = ambientRepository.getById(move.ambientId)
                ambientRepository.getAllByDepartment(Departament(ambient.departamentId))
                    .map { ambients ->
                        ambients.filter { it.id != ambient.id }
                    }
            }
            is Risk -> {
                val function = functionRepository.getById(move.functionId)
                functionRepository.getAllByAmbient(Ambient(function.ambientId))
                    .map { functions ->
                        functions.filter { it.id != function.id }
                    }
            }
            else -> {
                throw UnsupportedOperationException("")
            }
        }
    }

    fun changeSelectedItem(item: Any?) = _selectedItemId.postValue(item)

    fun move(move: Any, to: Any) {
        viewModelScope.launch {
            when (move) {
                is Ambient -> {
                    AmbientMoveChain(move, (to as Departament).id).startMove(_moveState)
                }
                is Function -> {
                    FunctionMoveChain(move, (to as Ambient).id).startMove(_moveState)
                }
                is Risk -> {
                    RiskMoveChain(move, (to as Function).id).startMove(_moveState)
                }
                else -> {
                    throw UnsupportedOperationException("")
                }
            }
        }
    }

}