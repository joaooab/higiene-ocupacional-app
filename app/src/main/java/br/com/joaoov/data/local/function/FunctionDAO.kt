package br.com.joaoov.data.local.function

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FunctionDAO {

    @Query("SELECT * FROM Function ORDER BY Name")
    fun getAll(): LiveData<List<FunctionLocal>>

    @Query("SELECT * FROM Function WHERE ambientId = :ambientId ORDER BY Name")
    fun getAllByAmbientId(ambientId: Long): LiveData<List<FunctionLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(function: FunctionLocal)

    @Delete
    suspend fun delete(function: FunctionLocal)

    @Update
    suspend fun update(function: FunctionLocal)

}
