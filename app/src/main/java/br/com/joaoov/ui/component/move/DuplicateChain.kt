package br.com.joaoov.ui.component.move

import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.ext.format
import br.com.joaoov.repository.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

interface DuplicateChain : KoinComponent {

    suspend fun duplicate(parentId: Long? = null)

}

class CompanyDuplicateChain(private val company: Company) : DuplicateChain {

    private val companyRepository: CompanyRepository by inject()

    override suspend fun duplicate(parentId: Long?) {
        val companyWithDepartaments = companyRepository.getCompanyWithRelation(company)
        val copy = company.copy(id = 0, date = Date().format())
        val newId = companyRepository.save(copy)
        companyWithDepartaments.departamentsWithAmbients.forEach {
            DepartamentDuplicateChain(it.departament).duplicate(newId)
        }
    }

}

class DepartamentDuplicateChain(private val departament: Departament) : DuplicateChain {

    private val departamentRepository: DepartamentRepository by inject()

    override suspend fun duplicate(parentId: Long?) {
        val departamentWithAmbients = departamentRepository.getDepartamentWithRelation(departament)
        val copy = if (parentId == null) {
            departament.copy(id = 0, date = Date().format())
        } else {
            departament.copy(id = 0, date = Date().format(), companyId = parentId)
        }
        val newId = departamentRepository.save(copy)
        departamentWithAmbients.ambientsWithFunctions.forEach {
            AmbientDuplicateChain(it.ambient).duplicate(newId)
        }
    }

}

class AmbientDuplicateChain(private val ambient: Ambient) : DuplicateChain {

    private val ambientRepository: AmbientRepository by inject()

    override suspend fun duplicate(parentId: Long?) {
        val ambientWithRelation = ambientRepository.getAmbientWithRelation(ambient)
        val copy = if (parentId == null) {
            ambient.copy(id = 0, date = Date().format())
        } else {
            ambient.copy(id = 0, date = Date().format(), departamentId = parentId)
        }
        val newId = ambientRepository.save(copy)
        ambientWithRelation.functionsWithRisks.forEach {
            FunctionDuplicateChain(it.function).duplicate(newId)
        }
    }

}

class FunctionDuplicateChain(private val function: Function) : DuplicateChain {

    private val functionRepository: FunctionRepository by inject()

    override suspend fun duplicate(parentId: Long?) {
        val functionWithRelation = functionRepository.getFunctionWithRelation(function)
        val copy = if (parentId == null) {
            function.copy(id = 0, date = Date().format())
        } else {
            function.copy(id = 0, date = Date().format(), ambientId = parentId)
        }
        val newId = functionRepository.save(copy)
        functionWithRelation.risks.forEach {
            RiskDuplicateChain(it).duplicate(newId)
        }
    }

}

class RiskDuplicateChain(private val risk: Risk) : DuplicateChain {

    private val riskRepository: RiskRepository by inject()

    override suspend fun duplicate(parentId: Long?) {
        val copy = if (parentId == null) {
            risk.copy(id = 0, date = Date().format())
        } else {
            risk.copy(id = 0, date = Date().format(), functionId = parentId)
        }
        riskRepository.save(copy)
    }

}