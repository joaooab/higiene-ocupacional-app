package br.com.joaoov.data.local.syncronize

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SyncronizeDAO {

    @Query("SELECT * FROM syncronize ORDER BY ID DESC LIMIT 1")
    suspend fun getLast(): SyncronizeLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(syncronize: SyncronizeLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(syncronize: List<SyncronizeLocal>)

}
