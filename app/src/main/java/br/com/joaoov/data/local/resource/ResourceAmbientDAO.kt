package br.com.joaoov.data.local.resource

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResourceAmbientDAO {

    @Query("SELECT * FROM resource_ambients")
    fun getAll(): LiveData<List<ResourceAmbientLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(resource: ResourceAmbientLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(resources: List<ResourceAmbientLocal>)

    @Delete
    suspend fun delete(resource: ResourceAmbientLocal)

    @Update
    suspend fun update(resource: ResourceAmbientLocal)

}
