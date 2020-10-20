package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.ambient.Ambient
import br.com.joaoov.data.ambient.AmbientDAO
import br.com.joaoov.data.ambient.toLocal
import br.com.joaoov.data.ambient.toModel
import br.com.joaoov.data.departament.Departament

interface AmbientRepository {

    fun getAmbients(departament: Departament): LiveData<List<Ambient>>

    suspend fun save(ambient: Ambient): Long

    suspend fun delete(ambient: Ambient)

}

class AmbientRepositoryImpl(private val dao: AmbientDAO) : AmbientRepository {

    override fun getAmbients(departament: Departament): LiveData<List<Ambient>> =
        Transformations.map(dao.getAllByDepartamentId(departament.id)) { it.toModel() }


    override suspend fun save(ambient: Ambient) =
        dao.save(ambient.toLocal())

    override suspend fun delete(ambient: Ambient) =
        dao.delete(ambient.toLocal())

}