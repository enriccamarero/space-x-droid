package com.ecamarero.spacex.network.launches.model

data class LaunchRequest(
    val launchYear: Int? = null,
    val launchSuccess: Boolean? = null,
    val order: String? = DEFAULT_ORDER
) {
    companion object {
        private const val DEFAULT_ORDER = "desc"
    }
}