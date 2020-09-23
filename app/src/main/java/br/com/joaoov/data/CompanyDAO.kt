package br.com.joaoov.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM Company ORDER BY Name")
    fun getAll(): LiveData<List<Company>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(ambient: Company): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(Ambient: List<Company>)

    @Delete
    fun delete(ambient: Company)

    @Update
    fun update(ambient: Company)

}
