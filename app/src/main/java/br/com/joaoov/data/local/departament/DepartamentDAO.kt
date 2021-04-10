package br.com.joaoov.data.local.departament

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DepartamentDAO {

    @Query("SELECT * FROM Departament WHERE id =:id")
    fun getById(id: Long): DepartamentLocal

    @Query("SELECT * FROM Departament ORDER BY id")
    fun getAll(): LiveData<List<DepartamentLocal>>

    @Query("SELECT * FROM Departament WHERE companyId = :companyId ORDER BY id")
    fun getAllByCompanyId(companyId: Long): LiveData<List<DepartamentLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(departament: DepartamentLocal)

    @Delete
    suspend fun delete(departament: DepartamentLocal)

    @Update
    suspend fun update(departament: DepartamentLocal)

}
