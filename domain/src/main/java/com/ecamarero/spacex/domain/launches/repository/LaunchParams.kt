package com.ecamarero.spacex.domain.launches.repository

data class LaunchParams(
    val launchYear: Int? = null,
    val launchSuccess: Boolean? = null,
    val order: Order = Order.Ascending
) {
    sealed class Order {
        object Ascending : Order()
        object Descending: Order()
    }
}
