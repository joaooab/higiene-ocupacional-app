package br.com.joaoov.data.local.risk

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.joaoov.data.local.resource.ResourceRisk
import br.com.joaoov.data.local.resource.ResourceRiskLocal

@Dao
interface RiskDAO {

    @Query("SELECT * FROM risk ORDER BY id")
    fun getAll(): LiveData<List<RiskLocal>>

    @Query("SELECT * FROM risk WHERE functionId = :functionId ORDER BY id")
    fun getAllByFuncionId(functionId: Long): LiveData<List<RiskLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(risk: RiskLocal) : Long

    @Delete
    suspend fun delete(risk: RiskLocal)

    @Update
    suspend fun update(risk: RiskLocal)
}