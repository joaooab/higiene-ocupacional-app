package br.com.joaoov.data.local.function

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.joaoov.data.local.report.FunctionWithRisksLocal

@Dao
interface FunctionDAO {

    @Query("SELECT * FROM Function WHERE id =:id")
    fun getById(id: Long): FunctionLocal

    @Query("SELECT * FROM Function WHERE id =:id")
    fun getFunctionWithRelation(id: Long): FunctionWithRisksLocal

    @Query("SELECT * FROM Function ORDER BY id")
    fun getAll(): LiveData<List<FunctionLocal>>

    @Query("SELECT * FROM Function WHERE ambientId = :ambientId ORDER BY id")
    fun getAllByAmbientId(ambientId: Long): LiveData<List<FunctionLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(function: FunctionLocal): Long

    @Delete
    suspend fun delete(function: FunctionLocal)

    @Update
    suspend fun update(function: FunctionLocal)

}
