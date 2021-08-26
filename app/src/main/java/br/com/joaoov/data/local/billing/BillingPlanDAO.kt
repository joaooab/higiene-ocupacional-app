package br.com.joaoov.data.local.billing

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BillingPlanDAO {

    @Query("SELECT * FROM billing_plan WHERE deleted = 0")
    fun getAll(): Flow<List<BillingPlanLocal>>

    @Query("SELECT * FROM billing_plan WHERE productId =:id AND deleted = 0")
    fun getByProductId(id: String): Flow<BillingPlanLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(plan: BillingPlanLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(plan: List<BillingPlanLocal>)

    @Delete
    suspend fun delete(plan: BillingPlanLocal)

    @Update
    suspend fun update(plan: BillingPlanLocal)

    @Query("DELETE FROM billing_plan")
    suspend fun clear()

}
