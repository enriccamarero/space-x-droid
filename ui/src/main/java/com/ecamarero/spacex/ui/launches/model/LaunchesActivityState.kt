package com.ecamarero.spacex.ui.launches.model

internal data class LaunchesActivityState(
    val launches: List<LaunchUI>? = null,
    val loading: Boolean = false,
    val error: Throwable? = null
)

