package com.ecamarero.spacex.domain.launches.datasource

data class LaunchParams(
    val launchYear: Int? = null,
    val launchSuccess: Boolean? = null,
    val order: String? = null
)