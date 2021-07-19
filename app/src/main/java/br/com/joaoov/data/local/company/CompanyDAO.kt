package br.com.joaoov.data.local.company

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.joaoov.data.local.report.CompanyWithDepartamentsLocal

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM company WHERE id =:id")
    fun getById(id: Long): CompanyLocal

    @Query("SELECT * FROM company WHERE userId =:userId ORDER BY id")
    fun getAll(userId: String): LiveData<List<CompanyLocal>>

    @Query("SELECT * FROM company WHERE id =:id")
    fun getCompanyWithRelation(id: Long): CompanyWithDepartamentsLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(company: CompanyLocal): Long

    @Delete
    suspend fun delete(company: CompanyLocal)

    @Update
    suspend fun update(company: CompanyLocal)

    @Query("SELECT * FROM company WHERE userId =''")
    fun getOldCompanies(): List<CompanyLocal>

}
