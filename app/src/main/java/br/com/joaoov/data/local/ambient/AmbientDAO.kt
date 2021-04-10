package br.com.joaoov.data.local.ambient

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.joaoov.data.local.report.AmbientWithFunctionsLocal

@Dao
interface AmbientDAO {

    @Query("SELECT * FROM Ambient WHERE id =:id")
    fun getById(id: Long): AmbientLocal

    @Query("SELECT * FROM Ambient ORDER BY id")
    fun getAll(): LiveData<List<AmbientLocal>>

    @Query("SELECT * FROM Ambient WHERE departamentId = :departamentId ORDER BY id")
    fun getAllByDepartamentId(departamentId: Long): LiveData<List<AmbientLocal>>

    @Query("SELECT * FROM Ambient WHERE id =:id")
    fun getAmbientWithRelation(id: Long): AmbientWithFunctionsLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(ambient: AmbientLocal): Long

    @Delete
    suspend fun delete(ambient: AmbientLocal)

    @Update
    suspend fun update(ambient: AmbientLocal)

}
