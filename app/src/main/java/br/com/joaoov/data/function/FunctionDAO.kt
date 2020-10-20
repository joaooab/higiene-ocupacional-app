package br.com.joaoov.data.function

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FunctionDAO {

    @Query("SELECT * FROM Function ORDER BY Name")
    fun getAll(): LiveData<List<FunctionLocal>>

    @Query("SELECT * FROM Function WHERE ambientId = :ambientId ORDER BY Name")
    fun getAllByAmbientId(ambientId: Long): LiveData<List<FunctionLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(function: FunctionLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(function: List<FunctionLocal>)

    @Delete
    fun delete(function: FunctionLocal)

    @Update
    fun update(function: FunctionLocal)

}
