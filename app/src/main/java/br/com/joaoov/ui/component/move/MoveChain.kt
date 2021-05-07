package br.com.joaoov.ui.component.move

import androidx.lifecycle.MutableLiveData
import br.com.joaoov.data.State
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.repository.AmbientRepository
import br.com.joaoov.repository.DepartamentRepository
import br.com.joaoov.repository.FunctionRepository
import br.com.joaoov.repository.RiskRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class MoveChain : KoinComponent {

    abstract suspend fun move()

    suspend fun startMove(state: MutableLiveData<State<Unit>>) {
        state.postValue(State.Loading())
        runCatching {
            move()
        }.onSuccess {
            state.postValue(State.Success(Unit))
        }.onFailure {
            state.postValue(State.Error(it))
        }
    }

}

class DepartamentMoveChain(private val departament: Departament, private val companyId: Long) :
    MoveChain() {

    private val departamentRepository: DepartamentRepository by inject()

    override suspend fun move() {
        DepartamentDuplicateChain(departament).duplicate(companyId)
        departamentRepository.delete(departament)
    }
}

class AmbientMoveChain(private val ambient: Ambient, private val departamentId: Long) :
    MoveChain() {

    private val ambientRepository: AmbientRepository by inject()

    override suspend fun move() {
        AmbientDuplicateChain(ambient).duplicate(departamentId)
        ambientRepository.delete(ambient)
    }

}

class FunctionMoveChain(private val function: Function, private val ambientId: Long) :
    MoveChain() {

    private val functionRepository: FunctionRepository by inject()

    override suspend fun move() {
        FunctionDuplicateChain(function).duplicate(ambientId)
        functionRepository.delete(function)
    }

}

class RiskMoveChain(private val risk: Risk, private val functionId: Long) :
    MoveChain() {

    private val riskRepository: RiskRepository by inject()

    override suspend fun move() {
        RiskDuplicateChain(risk).duplicate(functionId)
        riskRepository.delete(risk)
    }

}