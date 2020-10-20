package br.com.joaoov.data.company

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM Company ORDER BY Name")
    fun getAll(): LiveData<List<Company>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(company: Company): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(companies: List<Company>)

    @Delete
    fun delete(company: Company)

    @Update
    fun update(company: Company)

}
