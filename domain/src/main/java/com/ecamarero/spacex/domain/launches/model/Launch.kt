package com.ecamarero.spacex.domain.launches.model

data class Launch(
    val flightNumber: Int,
    val launchYear: Int,
    val launchSuccess: Boolean
)