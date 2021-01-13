package br.com.joaoov.data.local.resource

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResourceRiskDAO {

    @Query("SELECT * FROM resource_risks")
    fun getAll(): LiveData<List<ResourceRiskLocal>>

    @Query("SELECT * FROM resource_risks WHERE category = :category")
    fun getAllByCategory(category: String): LiveData<List<ResourceRiskLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(resourceRisk: ResourceRiskLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(resourceRisks: List<ResourceRiskLocal>)

    @Delete
    suspend fun delete(resourceRisk: ResourceRiskLocal)

    @Update
    suspend fun update(resourceRisk: ResourceRiskLocal)

}
