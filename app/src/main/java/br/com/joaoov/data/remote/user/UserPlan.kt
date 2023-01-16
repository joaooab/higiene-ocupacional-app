package br.com.joaoov.data.remote.user

sealed class UserPlan(val token: String) {
    abstract val id: String

    class Basic(tokenPurchase: String = "") : UserPlan(tokenPurchase) {
        override val id: String = "plan_basic"
    }
}