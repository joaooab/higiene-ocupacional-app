package br.com.joaoov.data.local.resource

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResourceAgentDAO {

    @Query("SELECT * FROM resource_agents")
    fun getAll(): LiveData<List<ResourceAgentLocal>>

//    fun getAllByCategory(category: String): LiveData<List<ResourceAgentLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(resource: ResourceAgentLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(resources: List<ResourceAgentLocal>)

    @Delete
    suspend fun delete(resource: ResourceAgentLocal)

    @Update
    suspend fun update(resource: ResourceAgentLocal)

}
