package br.com.joaoov.data.local.resource

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResourceAmbientDAO {

    @Query("SELECT * FROM resource_ambients")
    fun getAll(): LiveData<List<ResourceAmbientLocal>>

    @Query("SELECT * FROM resource_ambients WHERE category = :category ORDER BY name")
    fun getAllByCategory(category: String): LiveData<List<ResourceAmbientLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(resources: List<ResourceAmbientLocal>)

    @Delete
    suspend fun delete(resources: List<ResourceAmbientLocal>)

    @Update
    suspend fun update(resource: ResourceAmbientLocal)

    @Query("DELETE FROM resource_ambients")
    suspend fun clear()
}
