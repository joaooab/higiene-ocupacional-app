package br.com.joaoov.data.remote.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class UserPlan(val token: String) : Parcelable {
    abstract val id: String

    @Parcelize
    class Basic(tokenPurchase: String = "") : UserPlan(tokenPurchase) {
        override val id: String = "plan_basic"
    }
}