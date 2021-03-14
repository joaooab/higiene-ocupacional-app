package br.com.joaoov.data.local.company

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM company ORDER BY id")
    fun getAll(): LiveData<List<CompanyLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(company: CompanyLocal)

    @Delete
    suspend fun delete(company: CompanyLocal)

    @Update
    suspend fun update(company: CompanyLocal)

}
