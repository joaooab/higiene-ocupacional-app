package br.com.joaoov.data.local.risk

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.joaoov.data.local.resource.ResourceRisk
import br.com.joaoov.data.local.resource.ResourceRiskLocal

@Dao
interface RiskDao {

    @Query("SELECT * FROM risk")
    fun getAll(): LiveData<List<RiskLocal>>

    @Query("SELECT * FROM risk WHERE functionId = :functionId")
    fun getAllByFuncionId(functionId: Long): LiveData<List<RiskLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(risk: RiskLocal): Long

    @Delete
    fun delete(risk: RiskLocal)

    @Update
    fun update(risk: RiskLocal)
}