package br.com.joaoov.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AmbientDAO {

    @Query("SELECT * FROM Ambient ORDER BY EMPRESA")
    fun getAll(): LiveData<List<Ambient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(ambient: Ambient): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(Ambient: List<Ambient>)

    @Delete
    fun delete(ambient: Ambient)

    @Update
    fun update(ambient: Ambient)

}
