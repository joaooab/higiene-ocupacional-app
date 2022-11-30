package br.com.joaoov.data.local.billing

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "billing_plan")
data class BillingPlanLocal(
    @PrimaryKey
    val productId: String,
    val name: String,
    val price: Long,
    val deleted: Boolean
)

fun BillingPlanLocal.toModel() = BillingPlan(
    productId = productId,
    name = name,
    price = price,
    deleted = deleted
)