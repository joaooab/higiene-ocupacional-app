package br.com.joaoov.data.departament

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DepartamentDAO {

    @Query("SELECT * FROM Departament ORDER BY Name")
    fun getAll(): LiveData<List<DepartamentLocal>>

    @Query("SELECT * FROM Departament WHERE companyId = :companyId ORDER BY Name")
    fun getAllByCompanyId(companyId: Long): LiveData<List<DepartamentLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(departament: DepartamentLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(departament: List<DepartamentLocal>)

    @Delete
    fun delete(departament: DepartamentLocal)

    @Update
    fun update(departament: DepartamentLocal)

}
