package com.ecamarero.spacex.domain.launches.repository

data class LaunchParams(
    val launchYears: Collection<Int> = emptyList(),
    val onlySuccessful: Boolean = false,
    val order: Order = Order.Ascending
) {
    sealed class Order {
        object Ascending : Order()
        object Descending: Order()
    }
}
