package br.com.joaoov.data.remote.user

import android.os.Parcelable
import br.com.joaoov.data.local.billing.Billing
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val username: String,
    val password: String,
    val name: String,
    val productId: String,
    val companyId: String? = null,
    val enabled: Boolean = true,
    val role: String = "",
    val document: String? = null,
    val accessKey: String? = null
) : Parcelable

fun User?.isLegalEntity() = !this?.document.isNullOrBlank()

fun User?.isPhysicalUser() = !isLegalEntity()

fun User?.isCompanyUser() = isPhysicalUser() && hasAccessKey()

fun User?.hasAccessKey() = !this?.accessKey.isNullOrBlank()

fun User?.isTrialPlan() = this?.productId == Billing.DEFAULT_PRODUCT

fun User.toNetwork() = UserNetwork(
    username = username,
    password = password,
    name = name,
)
