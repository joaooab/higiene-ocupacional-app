package br.com.joaoov.data.local.ambient

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AmbientDAO {

    @Query("SELECT * FROM Ambient ORDER BY LOCAL")
    fun getAll(): LiveData<List<AmbientLocal>>

    @Query("SELECT * FROM Ambient WHERE departamentId = :departamentId ORDER BY LOCAL")
    fun getAllByDepartamentId(departamentId: Long): LiveData<List<AmbientLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(ambient: AmbientLocal)

    @Delete
    suspend fun delete(ambient: AmbientLocal)

    @Update
    suspend fun update(ambient: AmbientLocal)

}
