package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.ambient.AmbientDAO
import br.com.joaoov.data.local.ambient.toLocal
import br.com.joaoov.data.local.ambient.toModel
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.report.AmbientWithFunctions
import br.com.joaoov.data.local.report.toModel

interface AmbientRepository {

    fun getById(ambientId: Long): Ambient

    fun getAllByDepartment(departament: Departament): LiveData<List<Ambient>>

    suspend fun getAmbientWithRelation(ambient: Ambient): AmbientWithFunctions

    suspend fun save(ambient: Ambient): Long

    suspend fun delete(ambient: Ambient)

    suspend fun update(ambient: Ambient)

}

class AmbientRepositoryImpl(private val dao: AmbientDAO) : AmbientRepository {

    override fun getById(ambientId: Long) = dao.getById(ambientId).toModel()

    override fun getAllByDepartment(departament: Departament): LiveData<List<Ambient>> =
        dao.getAllByDepartamentId(departament.id).map { it.toModel() }

    override suspend fun getAmbientWithRelation(ambient: Ambient) =
        dao.getAmbientWithRelation(ambient.id).toModel()

    override suspend fun save(ambient: Ambient) =
        dao.save(ambient.toLocal())

    override suspend fun delete(ambient: Ambient) =
        dao.delete(ambient.toLocal())

    override suspend fun update(ambient: Ambient) =
        dao.update(ambient.toLocal())
}